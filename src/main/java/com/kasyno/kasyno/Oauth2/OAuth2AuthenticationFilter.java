package com.kasyno.kasyno.Oauth2;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.kasyno.kasyno.jwt.JwtConfig;
import com.kasyno.kasyno.jwt.JwtGenerator;
import com.kasyno.kasyno.security.ApplicationUserRole;
import com.kasyno.kasyno.user.User;
import com.kasyno.kasyno.user.UserRepository;
import com.kasyno.kasyno.user.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import java.util.Optional;


@Component
public class OAuth2AuthenticationFilter extends GenericFilterBean {

        private AntPathRequestMatcher requestMatcher = new AntPathRequestMatcher("/auth/{registrationId}", HttpMethod.POST.name());


        private final UserService userService;
        private final UserRepository userRepository;
        private final JwtGenerator jwtGenerator;
        private final JwtConfig jwtConfig;

        public OAuth2AuthenticationFilter(UserService userService, UserRepository userRepository, JwtGenerator jwtGenerator, JwtConfig jwtConfig) {
                this.userService = userService;
                this.userRepository = userRepository;
                this.jwtGenerator = jwtGenerator;
                this.jwtConfig = jwtConfig;
        }


        @Override
        public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

                HttpServletRequest request = (HttpServletRequest) req;
                HttpServletResponse response = (HttpServletResponse) res;

                if (!requireAuthentication(request)) {
                        chain.doFilter(request, response);
                        return;
                }

                String code = readCode(request);

                GoogleTokenResponse tokenResponse =
                        new GoogleAuthorizationCodeTokenRequest(
                                new NetHttpTransport(),
                                JacksonFactory.getDefaultInstance(),
                                "https://oauth2.googleapis.com/token",
                                "232184996385-jlgvk9t282s6tf0hl0maaqvu506elsi9.apps.googleusercontent.com",
                                "8ecnFhtAKZygVeu7dqSeeZbQ",
                                code,
                                "http://localhost:8080")
                                .execute();


                if (tokenResponse != null) {

                        GoogleIdToken idToken = tokenResponse.parseIdToken();
                        GoogleIdToken.Payload payload = idToken.getPayload();
                        String email = payload.getEmail();
                        String name = (String) payload.get("name");

                        Optional<User> userByEmail = userRepository.findUserByEmail(email);

                        if ( !userByEmail.isPresent() )
                        {
                                // Register
                                userService.addNewOAuth2User(email, name);
                        }


                        String token = jwtGenerator.generateToken(email, ApplicationUserRole.USER.getGrantedAuthorities());
                        response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + token);
                }
                else
                {
                        chain.doFilter(request, response);
                        return;
                }
        }


        private boolean requireAuthentication(ServletRequest request) {

                return requestMatcher.matches((HttpServletRequest) request);
        }

//        private OAuth2AuthenticationToken authenticate(HttpServletRequest request, HttpServletResponse response) {
//
//                String code;
//
//                try {
//                        code = readCode(request);
//                } catch (IOException e) {
//                        throw new IllegalArgumentException("authentication_code_missing");
//                }
//
//                Map<String, String> variables = requestMatcher.matcher(request).getVariables();
//                String registrationId = variables.get("registrationId");
//                ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(registrationId);
//
//                OAuth2AuthorizationRequest authorizationRequest = authorizationRequestResolver.resolve(request, registrationId);
//
//                OAuth2AuthorizationResponse authorizationResponse = OAuth2AuthorizationResponse
//                        .success(code)
//                        .redirectUri("postmessage")
//                        .state(authorizationRequest.getState())
//                        .build();
//
//                OAuth2LoginAuthenticationToken authenticationRequest = new OAuth2LoginAuthenticationToken(
//                        clientRegistration, new OAuth2AuthorizationExchange(authorizationRequest, authorizationResponse));
//
//
//
//                Authentication authenticate = authenticationManager.authenticate(authenticationRequest);
//                OAuth2LoginAuthenticationToken authenticationResult = (OAuth2LoginAuthenticationToken) authenticate;
//
//
//                String username = authenticationResult.getPrincipal().getName();
//                String email = authenticationResult.getPrincipal().getAttribute("email");
//                // TODO: Stwórz użytkownika jeśli nie istnieje
//
//                OAuth2AuthenticationToken oauth2Authentication = new OAuth2AuthenticationToken(
//                        authenticationResult.getPrincipal(),
//                        ApplicationUserRole.USER.getGrantedAuthorities(),
//                        authenticationResult.getClientRegistration().getRegistrationId());
//
//                OAuth2AuthorizedClient authorizedClient = new OAuth2AuthorizedClient(
//                        authenticationResult.getClientRegistration(),
//                        oauth2Authentication.getName(),
//                        authenticationResult.getAccessToken(),
//                        authenticationResult.getRefreshToken());
//
//                return oauth2Authentication;
//        }


        private String readCode(HttpServletRequest request) throws IOException {
                ObjectMapper objectMapper = new ObjectMapper();

                Map<String, String> authRequest = objectMapper.readValue(request.getReader(), Map.class) ;
                String code = authRequest.get("code");
                return code;
        }
}
