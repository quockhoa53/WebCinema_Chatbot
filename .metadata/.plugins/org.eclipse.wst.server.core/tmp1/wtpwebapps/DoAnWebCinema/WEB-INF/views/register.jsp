<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sign Up</title>
    
    <!-- External CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" 
          integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g==" 
          crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" href="http://localhost:9999/DoAnWebCinema/css/bootstrap.min.css" />
    <link rel="stylesheet" href="http://localhost:9999/DoAnWebCinema/css/register.css"/>

    <style>
        .page {
            background: url("https://assets.nflxext.com/ffe/siteui/vlv3/5523db5a-e2b2-497f-a88b-61f175c3dbad/924da883-2be9-48cc-84cc-45dbf5c7d2da/VN-vi-20230306-popsignuptwoweeks-perspective_alpha_website_large.jpg") no-repeat;
            background-size: auto;
        }

        .dropdown:hover .dropdown-menu {
            display: block;
            margin-top: 0;
        }

        .icon {
            color: #fff;
        }
        .error-message, .errors{
        	color: red;
        }
        
    </style>
</head>

<body class="page">
    <div class="wrap">
        <div class="login" style="margin-top: 20%;">
            <h2 class="login-header">Sign Up</h2>
            <div class="error-message">${message}</div>

            <form:form class="login-form" method="POST" 
                       action="/DoAnWebCinema/register/insert.htm" 
                       modelAttribute="KhachHang">
                
                <!-- Name Field -->
                <div class="form-group">
                    <label class="form-label">Name<span class="required">*</span></label>
                    <div class="input-container">
                        <i class="fa-solid fa-user icon"></i>
                        <form:input type="text" pattern="[^\d]+"
                        		  id="customer_name"
                                  class="input-content" 
                                  placeholder="Full Name" 
                                  path="tenKH" />
                        <form:errors path="tenKH" class="errors" />
                    </div>
                </div>

                <!-- Gender Field -->
                <div class="form-group">
                    <div class="input-container">
                        <i class="fa-solid fa-globe icon"></i>
                        <form:select path="gioiTinh" class="select input-content" id="customer_gender">
                            <form:option value="true">Male</form:option>
                            <form:option value="false">Female</form:option>
                        </form:select>
                    </div>
                </div>

                <!-- Phone Field -->
                <div class="form-group">
                    <div class="input-container">
                        <i class="fa-solid fa-phone icon"></i>
                        <form:input type="text"
                        		  id="customer_phone"
                                  class="input-content" 
                                  placeholder="Phone Number" 
                                  path="soDT" />
                        <form:errors path="soDT" class="errors" />
                    </div>
                </div>

                <!-- Email Field -->
                <div class="form-group">
                    <div class="input-container">
                        <i class="fa-solid fa-envelope icon"></i>
                        <form:input type="email"
                        		  id="customer_email" 
                                  class="input-content" 
                                  placeholder="Email" 
                                  path="dstaikhoan.email"/>
                        <form:errors path="dstaikhoan.email" class="errors" />
                    </div>
                </div>

                <!-- Password Field -->
                <div class="form-group">
                    <div class="input-container">
                        <i class="fa-solid fa-lock icon"></i>
                        <form:input type="password" 
                        		  id="customer_password" 
                                  class="input-content" 
                                  placeholder="Password" 
                                  path="dstaikhoan.password"/>
                        <form:errors path="dstaikhoan.password" class="errors" />
                    </div>
                </div>

                <!-- Address Field -->
                <div class="form-group">
                    <div class="input-container">
                        <i class="fa-solid fa-globe icon"></i>
                        <form:select path="diaChi" class="select" id="customer_address" >
                            <form:option value="">Select Area</form:option>
                            <form:option value="Ho Chi Minh">Ho Chi Minh</form:option>
                            <form:option value="Ha Noi">Hanoi</form:option>
                            <form:option value="Vung Tau">Vung Tau</form:option>
                            <form:option value="Dong Nai">Dong Nai</form:option>
                            <form:option value="Nam Dinh">Nam Dinh</form:option>
                            <form:option value="Dak Lak">Dak Lak</form:option>
                            <form:option value="Hai Phong">Hai Phong</form:option>
                            <form:option value="Thai Binh">Thai Binh</form:option>
                        </form:select>
                    </div>
                </div>

                <!-- Date of Birth -->
                <div class="form-group">
                    <label class="form-label">Date of Birth</label>
                    <div class="date-selectors">
                        <select name="day" class="select-date" id="customer_daybir" required>
                            <option value="">Date</option>
                            <% for(int i = 1; i <= 31; i++) { %>
                                <option value="<%= String.format("%02d", i) %>"><%= i %></option>
                            <% } %>
                        </select>
                        <select name="month" class="select-date" id="customer_monthbir" required>
                            <option value="">Month</option>
                            <% for(int i = 1; i <= 12; i++) { %>
                                <option value="<%= String.format("%02d", i) %>"><%= i %></option>
                            <% } %>
                        </select>
                        <select name="year" class="select-date" id="customer_yearbir" required>
                            <option value="">Year</option>
                            <% for(int year = 1990; year <= 2020; year++) { %>
                                <option value="<%= year %>"><%= year %></option>
                            <% } %>
                        </select>
                    </div>
                </div>

                <!-- Submit Button -->
                <div class="submit-row">
                    <button id="btn-submit" type="submit" class="btn-submit">Sign up</button>
                </div>
            </form:form>

            <div class="login-footer">
                Do you already have an account? 
                <a href="http://localhost:9999/DoAnWebCinema/login.htm" class="sign-up-link">Login</a>
            </div>
        </div>
    </div>

    <!-- Modal for successful registration -->
    <div class="modal fade" id="registerSuccessModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Registration Successful</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            You have successfully registered. You can log in to continue using the service.
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            <a href="http://localhost:9999/DoAnWebCinema/login.htm" class="btn btn-primary">Login</a>
          </div>
        </div>
      </div>
    </div>

	<!-- External JavaScript -->
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>

    <!-- Script to show the modal on successful registration -->
    <script type="text/javascript">
        <% if ("success".equals(request.getAttribute("message"))) { %>
            $(document).ready(function() {
                $('#registerSuccessModal').modal('show');
            });
        <% } %>
    </script>
</body>
</html>
