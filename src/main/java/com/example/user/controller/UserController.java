package com.example.user.controller;
import com.example.user.entity.AddressInfo;
import com.example.user.entity.Session;
import com.example.user.entity.UserInfo;
import com.example.user.model.*;
import com.example.user.service.AddressService;
import com.example.user.service.SessionService;
import com.example.user.service.UserService;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    SessionService sessionService;
    @Autowired
    AddressService addressService;

    private final String orderUrl = "http://172.16.20.77:8080/addorder/";
    private final String cartUrl = "http://172.16.20.61:8080/cart/";

    @PostMapping(path="/user/login")
    public GenericResponse login(@RequestBody AuthForm loginForm, HttpSession session){
        UserInfo result = userService.userLogin(loginForm);
        GenericResponse response = new GenericResponse();
        response.setAccesstoken(session.getId());
        if(result == null) {
            response.setStatus("failure");
            return response;
        }
        else{
            Session newsession = new Session();
            newsession.setSession_id(session.getId());
            newsession.setUid(result.getUid());
            sessionService.setSession(newsession);
            response.setStatus("success");
        }
        System.out.println("login sessionid "+session.getId());
        result.setPassword("");
        response.setPayload(result);
        return response;
    }

    @PostMapping(path="/user/signup")
    public GenericResponse signup(@RequestBody SignupForm signupForm, HttpSession session){
        UserInfo result = userService.userSignup(signupForm);
        GenericResponse response = new GenericResponse();
        response.setAccesstoken(session.getId());
        if(result == null) {
            response.setStatus("failure");
            return response;
        }
        else{
            Session newsession = new Session();
            newsession.setSession_id(session.getId());
            newsession.setUid(result.getUid());
            sessionService.setSession(newsession);
            response.setStatus("success");
        }
        result.setPassword("");
        response.setPayload(result);
        return response;
    }

    @GetMapping(path="/user/getuser/{session_id}")
    public Integer getuser(@PathVariable String session_id){
        Session sess = sessionService.readSession(session_id);
        if(sess == null)
            return null;
        else
            return sess.getUid();
    }

    @GetMapping(path="/user/getalladdress")
    public GenericResponse getalladdresss(@RequestParam(required = false) String accesstoken, HttpSession session){
        if(accesstoken == null) accesstoken = session.getId();
        List<String> addresses = new ArrayList<String>();
        GenericResponse response = new GenericResponse();
        Integer uid = getuser(accesstoken);
        System.out.println(uid);
        if(uid == null){
            System.out.println("User does not exist");
            response.setStatus("failure");
        }
        addresses = addressService.readAddressAll(uid);
        response.setStatus("success");
        AddressList addressList = new AddressList();
        addressList.setAddList(addresses.toArray());
        response.setPayload(addressList);
        return response;
    }

    @PostMapping(path="/user/address/add")
    public GenericResponse addadddress(@RequestParam(required = false) String accesstoken, @RequestParam String address, HttpSession session){
        if(accesstoken == null) accesstoken = session.getId();
        GenericResponse response = new GenericResponse();
        Integer uid = sessionService.readSession(accesstoken).getUid();
        if(uid == null){
            response.setStatus("failure");
            return response;
        }
        AddressInfo newadd = new AddressInfo();
        newadd.setAddress(address);
        newadd.setUid(uid);
        AddressInfo addressInfoN =  addressService.addAddress(newadd);
        response.setStatus("success");
        response.setPayload(addressInfoN);
        return response;
    }

    @DeleteMapping(path="/user/address/delete")
    public GenericResponse deleteaddress(@RequestParam(required = false) String accesstoken, @RequestParam String address, HttpSession session){
        if(accesstoken == null) accesstoken = session.getId();
        GenericResponse response = new GenericResponse();
        Session sess = sessionService.readSession(accesstoken);
        if(sess == null){
            response.setStatus("failure");
            return response;
        }
        Integer uid = sess.getUid();
        AddressInfo deladd = new AddressInfo();
        deladd.setAddress(address);
        deladd.setUid(uid);
        addressService.deleteAddress(deladd);
        response.setStatus("success");
        return response;
    }
    @GetMapping(path="/user/profile")
    public GenericResponse getprofile(@RequestParam(required = false) String accesstoken, HttpSession session){
        UserInfo uinfo = new UserInfo();
        Object[] orderObs = new Object[0];
        GenericResponse response = new GenericResponse();
        if(accesstoken == null) accesstoken = session.getId();
        Session sess = sessionService.readSession(accesstoken);
        if(sess == null){
            System.out.println("Session not found");
            response.setStatus("failure");
            return response;
        }
        System.out.println("Session found");
        Integer uid = sess.getUid();
        uinfo = userService.getUserById(uid);
        uinfo.setPassword("");
        RestTemplate restTemplate = new RestTemplate();
        /*try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(orderUrl).queryParam("id", 2);
            ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(builder.toUriString(), Object[].class);
            orderObs = responseEntity.getBody();
        }
        catch(Exception e){
            e.printStackTrace();
        }*/
        List<String> addresses = addressService.readAddressAll(uid);
        UserProfile userProfile = new UserProfile();
        userProfile.setOrderHistory(orderObs);
        userProfile.setUserInfo(uinfo);
        userProfile.setAddressList(addresses.toArray());
        response.setStatus("success");
        response.setPayload(userProfile);
        return response;
    }

    @GetMapping(path="/user/cart/details")
    public GenericResponse getcart(@RequestParam(required = false) String accesstoken, HttpSession session) {
        GenericResponse response = new GenericResponse();
        if (accesstoken == null) accesstoken = session.getId();
        Session sess = sessionService.readSession(accesstoken);
        if (sess == null) {
            System.out.println("Session not found");
            response.setStatus("failure");
            return response;
        }
        System.out.println("Session found");
        Integer uid = sess.getUid();
        RestTemplate restTemplate = new RestTemplate();
        CartList cartlist = new CartList();
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(cartUrl + "details").queryParam("userId", uid);
            ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(builder.toUriString(), Object[].class);
            cartlist.setList(responseEntity.getBody());
        } catch (Exception e) {
            response.setStatus("failure");
            e.printStackTrace();
        }
        response.setPayload(cartlist);
        response.setStatus("success");
        return response;
    }

    @PostMapping(path="/user/cart/add")
    public GenericResponse addtocart(@RequestBody CartDTO cartDTOin, HttpSession session){
        GenericResponse response = new GenericResponse();
        String accesstoken = cartDTOin.getAccesstoken();
        if (accesstoken == null) accesstoken = session.getId();
        Session sess = sessionService.readSession(accesstoken);
        if (sess == null) {
            System.out.println("Session not found");
            response.setStatus("failure");
            return response;
        }
        System.out.println("Session found");
        Integer uid = sess.getUid();
        cartDTOin.setUserId(uid);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(cartDTOin, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> out;
        try {
             out = restTemplate.exchange(cartUrl + "add", HttpMethod.POST, entity
                    , String.class);
        }
        catch(Exception e){
            e.printStackTrace();
            response.setStatus("failure");
            return response;
        }
        response.setStatus(out.getBody());
        return response;
    }

    @PostMapping(path="/user/cart/delete")
    public GenericResponse deletefromcart(@RequestBody CartDTO item, HttpSession session){
        GenericResponse response = new GenericResponse();
        String accesstoken = item.getAccesstoken();
        if (accesstoken == null) accesstoken = session.getId();
        Session sess = sessionService.readSession(accesstoken);
        if (sess == null) {
            System.out.println("Session not found");
            response.setStatus("failure");
            return response;
        }
        System.out.println("Session found");
        Integer uid = sess.getUid();
        item.setUserId(uid);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(item,headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> out = restTemplate.exchange(cartUrl+"delete", HttpMethod.DELETE, entity
                , String.class);
        response.setStatus(out.getBody());
        return response;
    }

    @PostMapping(path="/user/order/buy")
    public GenericResponse deletefromcart(@RequestBody OrderDTO orderDTO, HttpSession session){
        GenericResponse response = new GenericResponse();
        String accesstoken = orderDTO.getAccesstoken();
        if (accesstoken == null) accesstoken = session.getId();
        Session sess = sessionService.readSession(accesstoken);
        if (sess == null) {
            System.out.println("Session not found");
            response.setStatus("failure");
            return response;
        }
        System.out.println("Session found");
        Integer uid = sess.getUid();
        System.out.println("user id: "+uid);
        orderDTO.setUserid(uid);
        orderDTO.setOrderdate(new Date(2019, 05, 11));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(orderDTO,headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> out;
        try {
             out = restTemplate.exchange(orderUrl, HttpMethod.POST, entity, String.class);
        }
        catch (Exception e){
            e.printStackTrace();
            response.setStatus("failure");
            return response;
        }
        System.out.println(out.getBody());
        response.setStatus(out.getBody());
        return response;
    }
}
