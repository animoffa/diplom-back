package com.study.petproject.service;

import com.study.petproject.model.Comment;
import com.study.petproject.model.ScrapeArticlesRequest;
import com.study.petproject.model.User;
import com.study.petproject.repo.UserRepo;
import org.apache.commons.logging.Log;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
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
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;

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

    }




    public String scrappingArticles(String targetURL, ScrapeArticlesRequest info) {
        ArrayList<String> articlesList = new ArrayList<String>(20);
        String Articles = "";
        for(Integer i=1; i<=info.pageCount; i++) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.71 Safari/537.36");
            headers.set("Connection", "keep-alive");
            headers.set("accept-language", "ru,en-US;q=0.9,en;q=0.8");
            //headers.set("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");


            String encodedFio = null;
            try {
                encodedFio = URLEncoder.encode(info.fio, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                System.out.println("UnsupportedEncodingException");
            }
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("itembox_name", "");
            map.add("auth", "");
            map.add("authorid", String.valueOf(info.id));
            map.add("authorhash", encodedFio);
            map.add("pagenum", String.valueOf(i));
            map.add("add_all", "");
            map.add("paysum", "");
            map.add("slight", "");
            map.add("show_refs", "1");
            map.add("hide_doubles", "1");
            map.add("items_all", "");
            map.add("publs_all", "");
            map.add("did", "1");
            map.add("urlnum", "1");
            map.add("rubric_order", "0");
            map.add("title_order", "0");
            map.add("org_order", "0");
            map.add("author_order", "0");
            map.add("year_order", "1");
            map.add("type_order", "0");
            map.add("role_order", "0");
            map.add("keyword_order", "0");
            map.add("show_option", "0");
            map.add("show_hash", "0");
            map.add("check_show_refs", "on");
            map.add("check_hide_doubles", "on");
            map.add("sortorder", "0");
            map.add("order", "1");
            map.add("itemboxid", "0");

            HttpEntity<MultiValueMap<String, String>> req = new HttpEntity<>(map, headers);
            RestTemplate template = new RestTemplate();
            final HttpComponentsClientHttpRequestFactory factory2 = new HttpComponentsClientHttpRequestFactory();
            final HttpClient httpClient = HttpClientBuilder.create()
                    .setRedirectStrategy(new LaxRedirectStrategy())
                    .build();

            factory2.setHttpClient(httpClient);
            template.setRequestFactory(factory2);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(targetURL);
            ResponseEntity<String> response = template.exchange(builder.build().encode().toUri(), HttpMethod.POST, req, String.class);

            System.out.println("URL ==== " + targetURL);
            System.out.println("RES STATUS === " + response.getStatusCode());
            System.out.println("RES BODY ==== " + response.getBody());
            articlesList.add(new String(response.getBody().getBytes(), StandardCharsets.UTF_8));
            Articles = Articles.concat(response.getBody());
        }

        return Articles;

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
