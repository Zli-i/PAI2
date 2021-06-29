<template>
    <div>
        <h1 class="warning--text">
            Oczekiwanie na potwierdzenie płatności, proszę czekać.
        </h1>
    </div>
</template>

<script>
import axios from 'axios';
import endpoint from '@/endpoint.json';

export default {
    props: ['user'],
    async created()
    {
        console.log('xd');

        const paymentId = this.$route.query.paymentId;
        const PayerID = this.$route.query.PayerID;

        
        console.log("paymentId", paymentId);
        console.log("PayerID", PayerID);
        console.log(`${endpoint.url}/paypal/complete/payment?paymentId=`+paymentId+"&PayerID="+PayerID);

        const response = await axios.post(`${endpoint.url}/paypal/complete/payment?paymentId=`+paymentId+"&PayerID="+PayerID,{} ,{
            headers: {
                    Authorization: localStorage.getItem('token')
            }
        }, ).catch((e) => {
            console.log(e);
            console.log(response)
            this.$router.push("/")
            location.reload()
        })

        
    }
}
</script>
