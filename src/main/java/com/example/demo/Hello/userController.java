package com.example.demo.Hello;

import com.example.demo.Hello.mybatis.SqlSessionLoader;
import com.example.demo.Hello.mybatis.po.User;
import com.example.demo.Hello.request.UserRegisterRequest;
import org.apache.ibatis.session.SqlSession;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class userController {

//    @RequestMapping(value = "/register", method = RequestMethod.POST)
//    public @ResponseBody
//    Object register1(@RequestBody UserRegisterRequest request){
//        if (/* success */ true) {
//            return new RegisterResponse("ok");
//            //RegisterResponse类请同学们自行定义
//        } else {
//            return new ErrorResponse("error");
//            //ErrorResponse类请同学们自行定义
//        }
//    }

    private class RegisterResponse {
        String response;
        public RegisterResponse(String string) {
            this.response = string;
        }

        public String getString() {
            return response;
        }

        public void setString(String string) {
            this.response = string;
        }
    }

    private class ErrorResponse {
        String error;
        public ErrorResponse(String string) {
            this.error = string;
        }
        public String getString() {
            return error;
        }

        public void setString(String string) {
            this.error = string;
        }
    }


    //注册
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody Object register(@RequestBody UserRegisterRequest request) throws IOException {
        SqlSession sqlSession = SqlSessionLoader.getSqlSession();
        User user = sqlSession.selectOne("hello.UserMapper.findUserByUsername", request.getUsername());
        if (user != null) {
            sqlSession.close();
            return new ErrorResponse("用户名已被占用！");
        } else {
            sqlSession.insert("hello.UserMapper.addUser", new User(request.getUsername(), request.getPassword(), request.getEmail(), request.getPhone()));
            sqlSession.commit();
            sqlSession.close();
            return new RegisterResponse("注册成功~"); // use your generated token here.
        }
    }

    //登录
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody Object login(@RequestBody UserRegisterRequest request) throws IOException {
        SqlSession sqlSession = SqlSessionLoader.getSqlSession();
        User user = sqlSession.selectOne("hello.UserMapper.isRegistered", request);
        if (user != null) {
            sqlSession.close();
            return new ErrorResponse("登录成功QAQ");
        } else {
            sqlSession.close();
            return new RegisterResponse("用户名或密码错误TAT");
        }
    }

    //获取所有用户信息
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public @ResponseBody Object listAll() throws IOException {
        SqlSession sqlSession = SqlSessionLoader.getSqlSession();
        List<User> users = sqlSession.selectList("hello.UserMapper.listAll");
        if (users.size() != 0) {
            sqlSession.close();
            return users;
        } else {
            sqlSession.close();
            return new RegisterResponse("暂无任何用户信息");
        }
    }
}