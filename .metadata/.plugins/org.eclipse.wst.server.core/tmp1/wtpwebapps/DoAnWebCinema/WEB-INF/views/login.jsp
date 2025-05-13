<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    
    <!-- External CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" 
          integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g==" 
          crossorigin="anonymous" 
          referrerpolicy="no-referrer" />
    <link rel="stylesheet" href="http://localhost:9999/DoAnWebCinema/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="http://localhost:9999/DoAnWebCinema/css/login.css"/>

    <style>
        .page {
            background-image: url("https://assets.nflxext.com/ffe/siteui/vlv3/5523db5a-e2b2-497f-a88b-61f175c3dbad/924da883-2be9-48cc-84cc-45dbf5c7d2da/VN-vi-20230306-popsignuptwoweeks-perspective_alpha_website_large.jpg");
            background-size: cover;
            background-repeat: no-repeat;
        }

        .dropdown:hover .dropdown-menu {
            display: block;
            margin-top: 0;
        }

        .icon {
            color: #fff;
        }
    </style>
</head>

<body class="page">
    <div class="form-tt">
        <h2>Login</h2>
        
        <!-- Error/Warning Messages -->
        <div style="color: red; font-style: italic; font-size: larger; margin-bottom: 5px;">
            ${messageA}
        </div>

        <!-- Login Form -->
        <form action="http://localhost:9999/DoAnWebCinema/login/login.htm" method="post" name="dang-nhap">
            <!-- Email Field -->
            <p id="errorEmail"  style="color: #ff3366; font-size: 12px; display: inline;">
                ${errorTK}
            </p>
            <div class="input-container">
                <i class="fa-solid fa-user icon"></i>
                <input class="input-content" 
                       type="text" 
                       name="email" 
                       placeholder="Enter registration email" 
                       required />
            </div>

            <!-- Password Field -->
            <p id="errorPassword" style="color: #ff3366; font-size: 12px; margin-top: 5px; margin-bottom: 0;">
                ${errorMK}
            </p>
            <div class="input-container">
                <i class="fa-solid fa-lock icon"></i>
                <input class="input-content" 
                       id="content-pass" 
                       type="password" 
                       name="password" 
                       placeholder="Enter password" 
                       required />
            </div>

            <!-- Forgot Password Link -->
            <a style="color: red;" href="/DoAnWebCinema/forget.htm">Forgot Password</a>

            <!-- Submit Button -->
            <input type="submit" name="submit" value="Login" />

            <!-- Sign Up Link -->
            <label class="psw-text">
                Don't have an account? 
                <a href="http://localhost:9999/DoAnWebCinema/register.htm" style="color: red;">Sign Up</a>
            </label>
        </form>
    </div>

    <!-- External JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
</body>
</html>