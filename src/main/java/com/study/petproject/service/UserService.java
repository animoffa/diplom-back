package com.study.petproject.service;

import com.study.petproject.model.Comment;
import com.study.petproject.model.User;
import com.study.petproject.repo.UserRepo;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class UserService extends ObjectService<User> {

    private final UserRepo userRepo;
    private final ArticleService articleService;
    private final PasswordEncoder passwordEncoder;
    private final CommentService commentService;

    public UserService(UserRepo userRepo, ArticleService articleService, CommentService commentService, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.articleService = articleService;
        this.commentService = commentService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        List<User> users = userRepo.findAll();
        if (users.isEmpty()) {
            User user = new User();
            user.name = "AAA";
            user.lastname = "BBB";
            user.about = "ABOUT";
            user.address = "ADDRESS";
            user.birthday = Instant.now();
            user.company = "COMPANY";
            user.email = "TEST@EMAIL.RU";
            user.password = "AAAA";
            user.phone = "+7123123123";
            user.status = "OKOKOKOKO";
            userRepo.save(user);
        }
    }

    @Override
    public void add(User obj) {
        userRepo.save(obj);
    }

    @Override
    public void edit(User obj) {
        userRepo.save(obj);
    }

    @Override
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @Override
    public Optional<User> getOne(long id) {
        return userRepo.findById(id);
    }

    @Override
    public void delete(long id) {
        userRepo.deleteById(id);
    }

    public void likeArticle(long articleId, long userId) {
        articleService.getOne(articleId)
                .ifPresent(existingArticle ->
                        getOne(userId).ifPresent(existingUser -> {
                            boolean alreadyLiked = existingArticle.likeList.stream()
                                            .anyMatch(user -> user.id.equals(userId));

                            if (alreadyLiked) {
                                existingArticle.likeList.removeIf(user -> user.id.equals(userId));
                            } else {
                                existingArticle.likeList.add(existingUser);
                            }

                            articleService.edit(existingArticle);
                        }));
    }
    private static String getDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }
    public String scrappingELibrary(String targetURL, String spin) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.71 Safari/537.36");
        headers.set("Connection", "keep-alive");

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("authors_all", "");
        map.add("pagenum", "");
        map.add("authorbox_name", "");
        map.add("selid", "");
        map.add("orgid", "");
        map.add("orgadminid", "");
        map.add("surname", "");
        map.add("codetype", "SPIN");
        map.add("codevalue", spin);
        map.add("town", "");
        map.add("countryid", "");
        map.add("orgname", "");
        map.add("rubriccode", "");
        map.add("metrics", "1");
        map.add("sortorder", "0");
        map.add("order", "0");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        RestTemplate template = new RestTemplate();
        final HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        final HttpClient httpClient = HttpClientBuilder.create()
                .setRedirectStrategy(new LaxRedirectStrategy())
                .build();
        factory.setHttpClient(httpClient);
        template.setRequestFactory(factory);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(targetURL);

        ResponseEntity<String> response = template.exchange(builder.build().encode().toUri(), HttpMethod.POST, request, String.class);

        System.out.println("TARGET URL ==== " + targetURL);
        System.out.println("RES STATUS CODE === " + response.getStatusCode());
        System.out.println("RES BODY ==== " + response.getBody());
        return response.getBody();


//        byte[] postData = urlParameters.getBytes( StandardCharsets.UTF_8 );
//        HttpURLConnection connection = null;
//        try {
//            //Create connection
//            URL url = new URL(targetURL);
//            connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Content-Type",
//                    "application/x-www-form-urlencoded");
//
////            connection.setRequestProperty("Content-Length",
////                    Integer.toString(urlParameters.getBytes().length));
////            connection.setRequestProperty("Content-Language", "en-US");
//
//            connection.setUseCaches(false);
//            connection.setDoOutput(true);
//
//            MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
//            map.add("authors_all", "");
//            map.add("pagenum", "");
//            map.add("authorbox_name", "");
//            map.add("selid", "");
//            map.add("orgid", "");
//            map.add("orgadminid", "");
//            map.add("surname", "");
//            map.add("codetype", "SPIN");
//            map.add("codevalue", "9042-5877");
//            map.add("town", "");
//            map.add("countryid", "");
//            map.add("orgname", "");
//            map.add("rubriccode", "");
//            map.add("metrics", "1");
//            map.add("sortorder", "0");
//            map.add("order", "0");
//
//
//            //Send request
//            DataOutputStream wr = new DataOutputStream (
//                    connection.getOutputStream());
//            wr.writeBytes(String.valueOf(map));
//            System.out.println("=================> ");
//            System.out.println(String.valueOf(map));
//            wr.close();
//
//            //Get Response
//            InputStream is = connection.getInputStream();
//            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
//            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
//            String line;
//            while ((line = rd.readLine()) != null) {
//                System.out.println(line);
//                response.append(line);
//                response.append('\r');
//            }
//            rd.close();
//            System.out.println(response.toString());
//            System.out.println("response.toString()");
//            return response.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("error   1");
//            return null;
//        } finally {
//            if (connection != null) {
//                connection.disconnect();
//            }
//        }
    }
    public void commentArticle (long articleId, long userId, String comment) {
        articleService.getOne(articleId)
                .ifPresent(existingArticle ->
                        getOne(userId).ifPresent(existingUser -> {
                            Comment _comment = new Comment();
                            _comment.author=existingUser;
                            _comment.date = Instant.now();
                            _comment.text = comment;
                            _comment = commentService.addComment(_comment);

                            existingArticle.commentList.add(_comment);


                            articleService.edit(existingArticle);
                        }));
    }

    public boolean register(User user) {
        Optional<User> existsUser = findUserByEmail(user.email);
        if (existsUser.isPresent()) {
            return false;
        }
        user.password = passwordEncoder.encode(user.password);
        add(user);
        return true;
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public boolean login(User user) {
        Optional<User> existsUser = findUserByEmail(user.email);
        if (existsUser.isEmpty()) {
            return false;
        }
        User passUser = existsUser.get();

        return passwordEncoder.matches(user.password, passUser.password);
    }
}
