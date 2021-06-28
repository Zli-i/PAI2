package com.kasyno.kasyno.paypal;

import com.kasyno.kasyno.user.UserService;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PayPalClient {
    String clientId = "ARDgFJ29_LAFoaRR0qo2xBBfGGp_l6NF3JYoJXXsmc1k5UG3Mv81Dv6QzWcx6L341tqWSrP9ld-iDWCD";
    String clientSecret = "EO51aeoVc1wm_qeGCmQMsrzPPyzZpU_Zv6sjfVfjaENYYlB98RPHbfwgyxuQd9tXPDMcSsIwZgbOO_vi";

    UserService userService;

    @Autowired
    public PayPalClient(UserService userService) {
        this.userService = userService;
    }

    public Map<String, Object> buyTokens (int bundle)
    {
        List<Item> list = new ArrayList<Item>();
        Item item = new Item();
        item.setQuantity("1");
        item.setCurrency("PLN");

        switch (bundle)
        {
            case 1:
                item.setName("Bundle 500");
                item.setDescription("500");
                item.setPrice("50");
                break;

            case 2:
                item.setName("Bundle 1000");
                item.setDescription("1000");
                item.setPrice("99");
                break;

            default:
                Map<String, Object> response = new HashMap<String, Object>();
                response.put("status", "error");
                return response;
        }
        list.add(item);

        ItemList itemList = new ItemList();
        itemList.setItems(list);

        String sum = itemList.getItems().get(0).getPrice();

        return createPayment(sum, itemList);
    }

    public Map<String, Object> createPayment(String sum, ItemList itemList){
        Map<String, Object> response = new HashMap<String, Object>();
        Amount amount = new Amount();
        amount.setCurrency("PLN");
        amount.setTotal(sum);



        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setItemList(itemList);
        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:8081/cancel");
        redirectUrls.setReturnUrl("http://localhost:8081/paypalSuccess");
        payment.setRedirectUrls(redirectUrls);
        Payment createdPayment;
        try {
            String redirectUrl = "";
            APIContext context = new APIContext(clientId, clientSecret, "sandbox");
            createdPayment = payment.create(context);
            if(createdPayment!=null){
                List<Links> links = createdPayment.getLinks();
                for (Links link:links) {
                    if(link.getRel().equals("approval_url")){
                        redirectUrl = link.getHref();
                        break;
                    }
                }
                response.put("status", "success");
                response.put("redirect_url", redirectUrl);
            }
        } catch (PayPalRESTException e) {
            System.out.println("Error happened during payment creation!");
        }
        return response;
    }

    public Map<String, Object> completePayment(HttpServletRequest req){
        Map<String, Object> response = new HashMap();

        String paymentId = req.getParameter("paymentId");
        String payerID = req.getParameter("PayerID");

        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerID);

        try {
            APIContext context = new APIContext(clientId, clientSecret, "sandbox");
            Payment createdPayment = payment.execute(context, paymentExecution);
            if(createdPayment!=null){
                String name = SecurityContextHolder.getContext().getAuthentication().getName();
                ItemList itemList = createdPayment.getTransactions().get(0).getItemList();
                userService.finalizeTransaction(name, itemList);

                response.put("status", "success");
                response.put("payment", createdPayment);
            }
        } catch (PayPalRESTException e) {
            System.err.println(e.getDetails());
        }
        return response;
    }
}
