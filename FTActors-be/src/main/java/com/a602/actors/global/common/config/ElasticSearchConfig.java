//package com.a602.actors.global.common.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.elasticsearch.client.ClientConfiguration;
//import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
//import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
//
//import javax.net.ssl.*;
//import java.security.KeyManagementException;
//import java.security.NoSuchAlgorithmException;
//import java.security.cert.X509Certificate;
//
//@Configuration
//@EnableElasticsearchRepositories
//public class ElasticSearchConfig extends ElasticsearchConfiguration {
//
//    @Value("${elasticsearch.host}")
//    private String host;
//
//    @Value("${elasticsearch.username}")
//    private String username;
//
//    @Value("${elasticsearch.password}")
//    private String password;
//
//    @Override
//    public ClientConfiguration clientConfiguration() {
//        return ClientConfiguration.builder()
//                .connectedTo(host+":"+ 9200)
////                .usingSsl() // ssl 사용
//                .withBasicAuth(username, password)
//                .build();
//    }
//
//    public static SSLContext disableSslVerification() { //보안 죽이는 거 같은데, 실제 환경에선 없애야 함
//        try {
//            // ============================================
//            // trust manager 생성(인증서 체크 전부 안함)
//            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
//                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                    return null;
//                }
//
//                public void checkClientTrusted(X509Certificate[] certs, String authType) {
//                }
//
//                public void checkServerTrusted(X509Certificate[] certs, String authType) {
//                }
//            }};
//
//            // trust manager 설치
//            SSLContext sc = SSLContext.getInstance("SSL");
//            sc.init(null, trustAllCerts, new java.security.SecureRandom());
//            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//
//            return sc;
//        } catch (NoSuchAlgorithmException | KeyManagementException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public static HostnameVerifier allHostsValid() {
//
//        // ============================================
//        // host name verifier 생성(호스트 네임 체크안함)
//        HostnameVerifier allHostsValid = (hostname, session) -> true;
//
//        // host name verifier 설치
//        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
//        return allHostsValid;
//
//    }
//}
