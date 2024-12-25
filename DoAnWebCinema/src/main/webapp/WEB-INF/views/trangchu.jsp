<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="custom" uri="http://example.com/custom" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Trang chủ</title>
	<base href="${pageContext.servletContext.contextPath}/">
	<link rel="icon" type="images/png" href="images/logocinema.png">
	<!--	Khai báo css, boostrap -->
	<link href="css/bootstrap.min.css" rel="stylesheet" >
	<link href="css/font-awesome.min.css" rel="stylesheet" >
	<link href="css/global.css" rel="stylesheet">
	<link href="css/index.css" rel="stylesheet">
	<link href="css/all.css" rel="stylesheet">
	<link href="css/chatbot.css" rel="stylesheet">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<link href="https://fonts.googleapis.com/css2?family=Rajdhani&display=swap" rel="stylesheet">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
	<script src="js/bootstrap.bundle.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/lightbox2@2.11.3/dist/css/lightbox.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/lightbox2@2.11.3/dist/js/lightbox.min.js"></script>
</head>
<body>
	<!-- 	Phần đầu -->
	<section id="top">
		<div class="container">
			 <div class="row top_1">
			 	<div class="col-md-3">
 				   <div class="top_1l pt-1">
		    			<h3 class="mb-0"><a class="text-white" href=""><i class="fa fa-video-camera col_red me-1"></i>Tứ Trụ Cinema</a></h3>
		   			</div>
			 	</div>
			 	<div class="col-md-5">
   	 				<div class="top_1m">
    					<form class="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3" action="/DoAnWebCinema/search.htm">
							<input type="search" class="form-control form-control-dark" placeholder="Tìm kiếm..." aria-label="Search" name="search">
						</form>
        			</div>
    			</div>
				<div class="col-md-4">
				    <div class="top_1r text-end mt-1">
				        <c:choose>
				            <c:when test="${user.tenKH != null}">
				                <p><i class="bi bi-person-circle"></i><span> </span><a href="customer/account.htm"><strong>${user.tenKH}</strong></a></p>
				            </c:when>
				            <c:otherwise>
				                <div>
				                    <i class="bi bi-person-circle"></i>
				                    <a href="login.htm"><b>Đăng Nhập</b></a>
				                    <a href="register.htm"><b>Đăng Ký</b></a>
				                </div>
				            </c:otherwise>
				        </c:choose>
				    </div>
				</div>
			 </div>
		</div>
	</section>
	<!-- 	Phần Menu -->
	<section id="header">
		<nav class="navbar navbar-expand-md navbar-light" id="navbar_sticky">
			<div class="container">
				<a class="navbar-brand text-white fw-bold" href=""><i class="fa fa-video-camera col_red me-1"></i>TỨ TRỤ CINEMA</a>
				    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
				    <span class="navbar-toggler-icon"></span>
			    </button>
    			<div class="collapse navbar-collapse" id="navbarSupportedContent">
					<ul class="navbar-nav mb-0">
			        	<li class="nav-item">
			          		<a class="nav-link active" aria-current="page" href="trangchu.htm">Trang chủ</a>
			        	</li>
			     		<li class="nav-item">
			          		<a id="dat-ve-link" class="nav-link" href="#">Đặt vé ngay</a>
			        	</li>
			        	<li class="nav-item dropdown">
			          		<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">Phim</a>
			          		<ul class="dropdown-menu drop_1" aria-labelledby="navbarDropdown">
						    	<li><a class="dropdown-item" href="phimsapchieu.htm?tab=home">Phim đang chiếu</a></li>
						    	<li><a class="dropdown-item border-0" href="phimsapchieu.htm?tab=profile">Phim sắp chiếu</a></li>
			          		</ul>
			        	</li>
						<li class="nav-item">
			          		<a class="nav-link" href="gioithieu.htm">Giới thiệu</a>
		        		</li>
						<li class="nav-item">
			          		<a class="nav-link" href="lienhe.htm">Liên hệ</a>
			        	</li>
			      	</ul>
				</div>
			</div>
		</nav>
	</section>
	<!-- 	Phần banner, 3 phim có điểm đánh giá cao nhất -->
	<section id="center" class="center_home">
		<div id="carouselExampleCaptions" class="carousel slide" data-bs-ride="carousel">
		  	<div class="carousel-indicators">
			    <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="0" class="active" aria-label="Slide 1"></button>
			    <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="1" aria-label="Slide 2" class="" aria-current="true"></button>
			    <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="2" aria-label="Slide 3"></button>
		  	</div>
		  	<div class="carousel-inner">
    			<c:forEach var="phim" items="${phimListHot}" varStatus="status" begin="0" end="2">
        			<div class="carousel-item ${status.index == 0 ? 'active' : ''}">
            			<img src="/DoAnWebCinema/uploads/${phim.linkAnh}" class="d-block w-100" alt="...">
            			<div class="carousel-caption d-md-block">
                			<h1 class="font_60">${phim.getTenPhim()}</h1>
              			    <h6 class="mt-3">
            					<c:choose>
            						<c:when test="${phim.getDiem() == null}">
							            <span class="col_red me-3">Chưa có đánh giá</span>
							        </c:when>
									<c:when test="${phim.getDiem() < 1}">
                    					<span class="col_red me-3">
                       						<i class="fa fa-star-half-o"></i>                  					
                       					</span>
									</c:when>
									<c:when test="${phim.getDiem() < 2}">
                   						<span class="col_red me-3">
                        					<i class="fa fa-star"></i>
                    					</span>
									</c:when>
									<c:when test="${phim.getDiem() < 3}">
                   						<span class="col_red me-3">
                        					<i class="fa fa-star"></i>
                        					<i class="fa fa-star"></i>
                    					</span>
									</c:when>
									<c:when test="${phim.getDiem() < 4}">
                   						<span class="col_red me-3">
                        					<i class="fa fa-star"></i>
                        					<i class="fa fa-star"></i>
                        					<i class="fa fa-star"></i>
                    					</span>
									</c:when>
									<c:when test="${phim.getDiem() < 4.5}">
                   						<span class="col_red me-3">
                        					<i class="fa fa-star"></i>
                        					<i class="fa fa-star"></i>
                        					<i class="fa fa-star"></i>
                        					<i class="fa fa-star"></i>
                    					</span>
									</c:when>
									<c:when test="${phim.getDiem() < 5}">
                   						<span class="col_red me-3">
                        					<i class="fa fa-star"></i>
                        					<i class="fa fa-star"></i>
                        					<i class="fa fa-star"></i>
                        					<i class="fa fa-star"></i>
                        					<i class="fa fa-star-half-o"></i> 
                    					</span>
									</c:when>
									<c:otherwise>
					                    <span class="col_red me-3">
					                        <i class="fa fa-star"></i>
					                        <i class="fa fa-star"></i>
					                        <i class="fa fa-star"></i>
					                        <i class="fa fa-star"></i>
					                        <i class="fa fa-star"></i>
					                    </span>
									</c:otherwise>
								</c:choose>
                   			    <c:if test="${phim.getDiem() != null}">
							        ${phim.getDiem()}/5 (Đánh giá)
							    </c:if>		Year : ${phim.getNamSX()}
                    			<a class="bg_red p-2 pe-4 ps-4 ms-3 text-white d-inline-block" href="/DoAnWebCinema/film/detail/${phim.maPhim}.htm">Chi tiết</a>
                			</h6>
                			<p class="mt-3">${phim.getMoTa()}</p>
                			<p class="mb-2"><span class="col_red me-1 fw-bold">Đạo diễn:</span>${phim.getDaoDien().getTenDaoDien()}</p>
             				<p class="mb-2"><span class="col_red me-1 fw-bold">Diễn viên:</span>
							    <c:if test="${not empty phim.vaiDiens}">
							        <c:forEach var="chiTietdienvien" items="${phim.vaiDiens}" varStatus="status">
							            <c:out value="${chiTietdienvien.getDienVien().tenDienVien}" />
							            <c:if test="${!status.last}">, </c:if>
							        </c:forEach>
							    </c:if>
							</p>
							<p class="mb-2"><span class="col_red me-1 fw-bold">Thể loại:</span>
							    <c:if test="${not empty phim.chiTietTheLoais}">
							        <c:forEach var="chiTiet" items="${phim.chiTietTheLoais}" varStatus="status">
							            <c:out value="${chiTiet.theLoai.tenTL}" />
							            <c:if test="${!status.last}">, </c:if>
							        </c:forEach>
							    </c:if>
							</p>
							<p><span class="col_red me-1 fw-bold">Thời gian:</span> ${phim.getThoiLuong()}m</p>
							<h6 class="mt-4">
			                	<a class="button" href="javascript:void(0)" onclick="openLightbox('${phim.getLinkTrailer()}', 'lightbox-${status.index}', 'lightbox-video-${status.index}')"><i class="fa fa-play-circle align-middle me-1"></i> Xem Trailer</a>
			                </h6>
			                <!-- Lightbox với ID riêng cho mỗi phim -->
                            <div id="lightbox-${status.index}" style="display:none; position:fixed; z-index:999; left:0; top:0; width:100%; height:100%; background-color:rgba(0,0,0,0.8); justify-content:center; align-items:center;">
                                <div style="position:relative; width:80%; max-width:800px;">
                                    <span onclick="closeLightbox('lightbox-${status.index}', 'lightbox-video-${status.index}')" style="position:absolute; top:10px; right:20px; color:white; font-size:30px; cursor:pointer;">&times;</span>
                                    <iframe id="lightbox-video-${status.index}" width="100%" height="450px" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
                                </div>
                            </div>
            		    </div>
          			</div>
    			</c:forEach>
			</div>
			<button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="prev">
			    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
			    <span class="visually-hidden">Previous</span>
			 </button>
			 <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="next">
			    <span class="carousel-control-next-icon" aria-hidden="true"></span>
			    <span class="visually-hidden">Next</span>
			  </button>
		</div>
	</section>
	<!-- 	Phim mới nhất -->
	<section id="trend" class="pt-4 pb-5">
		<div class="container">
			<div class="row trend_1">
	            <div class="col-md-6 col-6">
	                <div class="trend_1l">
	                    <h4 class="mb-0">
	                        <i class="fa fa-youtube-play align-middle col_red me-1"></i>
	                        <span class="col_red">Phim</span> mới nhất
	                    </h4>
	                </div>
	            </div>
	        </div>
	        <div class="row trend_2 mt-4">
	        	<div id="carouselExampleCaptions1" class="carousel slide" data-bs-ride="carousel">
	        		<div class="carousel-indicators">
			            <button type="button" data-bs-target="#carouselExampleCaptions1" data-bs-slide-to="0" class="active" aria-label="Slide 1"></button>
			            <button type="button" data-bs-target="#carouselExampleCaptions1" data-bs-slide-to="1" aria-label="Slide 2"></button>
			        </div>
			        <div class="carousel-inner">
			        	<c:forEach var="phim" items="${phimListNew}" varStatus="status" begin="0" end="5">
			            	<c:if test="${status.index % 3 == 0}">
                    			<div class="carousel-item ${status.index == 0 ? 'active' : ''}">
                        			<div class="trend_2i row">
                			</c:if>
                			<div class="col-md-4">
                                <!-- Hiển thị ảnh và nút để mở lightbox -->
                                <div class="trend_2im clearfix position-relative">
                                    <div class="trend_2im1 clearfix">
                                        <div class="grid">
                                            <figure class="effect-jazz mb-0">
                                                <!-- Sử dụng chỉ số phim để tạo ID riêng -->
                                                <a href="javascript:void(0)" onclick="openLightbox('${phim.getLinkTrailer()}', 'lightbox-${status.index}', 'lightbox-video-${status.index}')">
                                                    <img src="/DoAnWebCinema/uploads/${phim.linkAnh}" class="w-100" alt="${phim.getTenPhim()}">
                                                </a>
                                            </figure>
                                        </div>
                                    </div>
                                    <div class="trend_2im2 clearfix text-center position-absolute w-100 top-0">
                                        <span class="fs-1">
                                            <!-- Sử dụng chỉ số phim để tạo ID riêng -->
                                            <a class="col_red" href="javascript:void(0)" onclick="openLightbox('${phim.getLinkTrailer()}', 'lightbox-${status.index}', 'lightbox-video-${status.index}')">
                                                <i class="fa fa-youtube-play"></i>
                                            </a>
                                        </span>
                                    </div>
                                </div>
                                <!-- Lightbox với ID riêng cho mỗi phim -->
                                <div id="lightbox-${status.index}" style="display:none; position:fixed; z-index:999; left:0; top:0; width:100%; height:100%; background-color:rgba(0,0,0,0.8); justify-content:center; align-items:center;">
                                    <div style="position:relative; width:80%; max-width:800px;">
                                        <span onclick="closeLightbox('lightbox-${status.index}', 'lightbox-video-${status.index}')" style="position:absolute; top:10px; right:20px; color:white; font-size:30px; cursor:pointer;">&times;</span>
                                        <iframe id="lightbox-video-${status.index}" width="100%" height="450px" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
                                    </div>
                                </div>
                                <div class="trend_2ilast bg_grey p-3 clearfix">
                                    <h5><a class="col_red" href="#">${phim.getTenPhim()}</a></h5>
                                    <p class="mb-2">
                                        <c:set var="substringResult" value="${custom:safeSubstring(phim.getMoTa(), 100)}" />
                                        ${substringResult}...<a href = "/DoAnWebCinema/film/detail/${phim.maPhim}.htm"><b>[Xem chi tiết]</b></a>
                                    </p>
                                    <c:choose>
                                    	<c:when test="${phim.getMaTT() == 1}">
                                    		<b><p style="color: gray;">Đang chiếu tại rạp</p></b>
                                    	</c:when>
                                    	<c:when test="${phim.getMaTT() == 2}">
                                    		<b><p style="color: gray;">Dự kiến khởi chiếu <fmt:formatDate value="${phim.ngayKhoiChieu}" pattern="dd/MM/yyyy" /></p></b>
                                    	</c:when>
                                    	<c:when test="${phim.getMaTT() == 0}">
                                    		<b><p style="color: gray;">Ngừng khởi chiếu</p></b>
                                    	</c:when>
                                    </c:choose>
                                    <span class="col_red">
                                        <c:choose>
                                        	<c:when test="${phim.getDiem() == null}">
									            <span class="col_red me-3">Chưa có đánh giá</span>
									        </c:when>
											<c:when test="${phim.getDiem() < 1}">
		                    					<span class="col_red me-3">
		                       						<i class="fa fa-star-half-o"></i>                  					
		                       					</span>
											</c:when>
											<c:when test="${phim.getDiem() < 2}">
		                   						<span class="col_red me-3">
		                        					<i class="fa fa-star"></i>
		                    					</span>
											</c:when>
											<c:when test="${phim.getDiem() < 3}">
		                   						<span class="col_red me-3">
		                        					<i class="fa fa-star"></i>
		                        					<i class="fa fa-star"></i>
		                    					</span>
											</c:when>
											<c:when test="${phim.getDiem() < 4}">
		                   						<span class="col_red me-3">
		                        					<i class="fa fa-star"></i>
		                        					<i class="fa fa-star"></i>
		                        					<i class="fa fa-star"></i>
		                    					</span>
											</c:when>
											<c:when test="${phim.getDiem() < 4.5}">
		                   						<span class="col_red me-3">
		                        					<i class="fa fa-star"></i>
		                        					<i class="fa fa-star"></i>
		                        					<i class="fa fa-star"></i>
		                        					<i class="fa fa-star"></i>
		                    					</span>
											</c:when>
											<c:when test="${phim.getDiem() < 5}">
		                   						<span class="col_red me-3">
		                        					<i class="fa fa-star"></i>
		                        					<i class="fa fa-star"></i>
		                        					<i class="fa fa-star"></i>
		                        					<i class="fa fa-star"></i>
		                        					<i class="fa fa-star-half-o"></i> 
		                    					</span>
											</c:when>
											<c:otherwise>
							                    <span class="col_red me-3">
							                        <i class="fa fa-star"></i>
							                        <i class="fa fa-star"></i>
							                        <i class="fa fa-star"></i>
							                        <i class="fa fa-star"></i>
							                        <i class="fa fa-star"></i>
							                    </span>
											</c:otherwise>
										</c:choose>
                                    </span>
                                    <p class="mb-0">                   			    
	                                    <c:if test="${phim.getDiem() != null}">
								        	${phim.getDiem()}/5 (Đánh giá)
								    	</c:if>
							    	</p>
                                </div>
                            </div>
                			<c:if test="${status.index % 3 == 2 || status.last}">
                        			</div>
                    			</div>
                			</c:if>
			        	</c:forEach>
			        </div>
	        	</div>
	        </div>
		</div>
	</section>
	
	<section id="upcome" class="pt-4 pb-5">
	<div class="container">
		<div class="row trend_1">
	  		<div class="col-md-6 col-6">
	   			<div class="trend_1l">
	    			<h4 class="mb-0"><i class="fa fa-youtube-play align-middle col_red me-1"></i><span class="col_red">   Sự kiện</span> nổi bật</h4>
	   			</div>
	  		</div>
		 </div>
		 <div class="row trend_2 mt-4">
		    <div id="carouselExampleCaptions1" class="carousel slide" data-bs-ride="carousel">
		    	<div class="carousel-indicators">
				    <button type="button" data-bs-target="#carouselExampleCaptions1" data-bs-slide-to="0" class="active" aria-label="Slide 1"></button>
				    <button type="button" data-bs-target="#carouselExampleCaptions1" data-bs-slide-to="1" aria-label="Slide 2" class="" aria-current="true"></button>
		  		</div>
		  		<div class="carousel-inner">
				    <c:forEach var="sukien" items="${sukienList}" varStatus="status" begin="0" end="5">
				        <c:if test="${status.index % 3 == 0}">
				            <div class="carousel-item ${status.index == 0 ? 'active' : ''}">
				                <div class="trend_2i row">
				        </c:if>
				                    <div class="col-md-4">
				                        <div class="trend_2im clearfix position-relative">
				                            <div class="trend_2im1 clearfix">
				                                <div class="grid">
													<figure class="effect-jazz mb-0">
													    <a href="${sukien.getPosterSK()}" data-lightbox="image-1" data-title="${sukien.getTenSK()}">
													        <img src="${sukien.getPosterSK()}" class="w-100" alt="${sukien.getTenSK()}">
													    </a>
													</figure>
				                                </div>
				                            </div>
				                        </div>
				                        <div class="trend_2ilast bg_grey p-3 clearfix">
				                            <h5>
				                                <a class="col_red" href="#">${sukien.getTenSK()}</a>
				                            </h5>
				                            <p class="mb-2">
				                                <c:set var="substringResult" value="${custom:safeSubstring(sukien.getMoTaSK(), 50)}" />
				                                <p>${substringResult}... <a href = "sukienDetail.htm?id=${sukien.maSK}"><b>[Xem thêm]</b></a></p>
				                            </p>
				                        </div>
				                    </div>
				        <c:if test="${status.index % 3 == 2 || status.last}">
				                </div>
				            </div>
				        </c:if>
				    </c:forEach>
				</div>
			</div>
</section>

<section id="popular" class="pt-4 pb-5 bg_grey">
	<div class="container">
	 <div class="row trend_1">
	  <div class="col-md-6 col-6">
	   <div class="trend_1l">
	    <h4 class="mb-0"><i class="fa fa-youtube-play align-middle col_red me-1"></i> Danh mục <span class="col_red">Phim</span></h4>
	   </div>
	  </div>
       <div class="col-md-6 col-6">
           <div class="trend_1r text-end">
               <h6 class="mb-0"><a class="button" href="phim/phimsapchieu.htm?tab=home">Xem tất cả</a></h6>
           </div>
       </div>
	 </div>
	 <div class="row popular_1 mt-4">
		   <ul class="nav nav-tabs  border-0 mb-0">
			    <li class="nav-item">
			        <a href="#home" data-bs-toggle="tab" aria-expanded="false" class="nav-link active">
			            <span class="d-md-block">PHIM ĐANG CHIẾU</span>
			        </a>
			    </li>
			    <li class="nav-item">
			        <a href="#profile" data-bs-toggle="tab" aria-expanded="true" class="nav-link">
			            <span class="d-md-block">PHIM SẮP CHIẾU</span>
			        </a>
			    </li>
		  </ul>
	 </div>
 <div class="popular_2 row mt-4">
   <div class="tab-content">
		<div class="tab-pane active" id="home">
		<div class="popular_2i row">
		  <c:forEach var="phim" items="${phimListDangChieu}" varStatus="status" begin="0" end="3">
		      <div class="col-md-6">
		        <div class="popular_2i1 row">
		          <div class="col-md-4 col-4">
		            <div class="popular_2i1lm position-relative clearfix">
		              <div class="popular_2i1lm1 clearfix">
		                <div class="grid">
		                  <figure class="effect-jazz mb-0">
		                    <a href="/DoAnWebCinema/uploads/${phim.linkAnh}" data-lightbox="movie-gallery" data-title="${phim.getTenPhim()}">
							    <img src="/DoAnWebCinema/uploads/${phim.linkAnh}" class="w-100" alt="img25">
							</a>
		                  </figure>
		                </div>
		              </div>
		            </div>
		          </div>
		          <div class="col-md-8 col-8">
		            <div class="popular_2i1r">
		              <h5><a class="col_red" href="#"></a></h5>
		              <h6></h6>
		              <h6> ${phim.getDiem()}/10 Imdb<span class="ms-2"><i class="fa fa-star col_red me-1"></i></span> Năm : ${phim.getNamSX()} <span class="ms-2">Thời lượng: ${phim.getThoiLuong()}m</span></h6>
		              <h4 class="col_red">${phim.getTenPhim()}</h4>
		              <p>${phim.getMoTa()}</p>
		              <h6 class="mb-0">
					    <a class="button" href="/DoAnWebCinema/datve/${currentDate}.htm">Đặt vé ngay</a>
					  </h6>
		            </div>
		          </div>
		        </div>
		      </div>
		      </c:forEach>
		    </div>
		</div>

    <div class="tab-pane" id="profile">
    <div class="popular_2i row">
        <c:forEach var="phim" items="${phimListSapChieu}" varStatus="status" begin="0" end="3">
            <div class="col-md-6">
                <div class="popular_2i1 row">
                    <div class="col-md-4 col-4">
                        <div class="popular_2i1lm position-relative clearfix">
                            <div class="popular_2i1lm1 clearfix">
                                <div class="grid">
                                    <figure class="effect-jazz mb-0">
                                      	<a href="/DoAnWebCinema/uploads/${phim.linkAnh}" data-lightbox="movie-gallery" data-title="${phim.getTenPhim()}">
										    <img src="/DoAnWebCinema/uploads/${phim.linkAnh}" class="w-100" alt="img25">
										</a>
                                    </figure>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-8 col-8">
                        <div class="popular_2i1r">
                            <h5><a class="col_red" href="#"></a></h5>
                            <h6></h6>
                            <h6> ${phim.getDiem()}/10 Imdb<span class="ms-2"><i class="fa fa-star col_red me-1"></i></span> Năm : ${phim.getNamSX()} <span class="ms-2">Thời lượng: ${phim.getThoiLuong()}m</span></h6>
                            <h4 class="col_red">${phim.getTenPhim()}</h4>
                            <p>${phim.getMoTa()}</p>
                            <b><p>Ngày khởi chiếu: ${phim.getNgayKhoiChieu()}</p></b>
                            <h6 class="mb-0">
								<a class="button" href="javascript:void(0)" onclick="openLightbox('${phim.getLinkTrailer()}', 'lightbox-${status.index}', 'lightbox-video-${status.index}')"><i class="fa fa-play-circle align-middle me-1"></i> Xem Trailer</a>
                            </h6>
                            <!-- Lightbox với ID riêng cho mỗi phim -->
                            <div id="lightbox-${status.index}" style="display:none; position:fixed; z-index:999; left:0; top:0; width:100%; height:100%; background-color:rgba(0,0,0,0.8); justify-content:center; align-items:center;">
                                <div style="position:relative; width:80%; max-width:800px;">
                                    <span onclick="closeLightbox('lightbox-${status.index}', 'lightbox-video-${status.index}')" style="position:absolute; top:10px; right:20px; color:white; font-size:30px; cursor:pointer;">&times;</span>
                                    <iframe id="lightbox-video-${status.index}" width="100%" height="450px" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</div>
 </div>
</div>
</section>


<section id="play">
<div class="play_m clearfix">
 <div class="container">
 <div class="row trend_1">
  <div class="col-md-12">
   <div class="trend_1l">
    <h4 class="mb-0"><i class="fa fa-youtube-play align-middle col_red me-1"></i> Top <span class="col_red">1</span> phòng vé</h4>
   </div>
  </div>
 </div>
 <div class="play2 row mt-4">
  <div class="col-md-4 p-0">
   <div class="play2l">
<div class="grid clearfix">
    <figure class="effect-jazz mb-0">
			<a href="#">
                <iframe 
                    id="videoID" 
                    height="515" 
                    class="w-100" 
                    frameborder="0" 
                    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" 
                    allowfullscreen>
                </iframe>
            </a>
    </figure>
</div>
   </div>
  </div>
  <div class="col-md-8 p-0">
   <div class="play2r  bg_grey p-4">
	   <h3><span class="col_red">Phim hot nhất thời điểm hiện tại:</span> ${phimListHot[0].getTenPhim()}</span><br></h3>
      <h5 class="mt-3">Nội dung</h5>
	  <hr class="line">
	  <p class="mt-3">${phimListHot[0].getMoTa()}</p>
	  <div class="play2ri row mt-4">
	   <div class="col-md-6">
	    <div class="play2ril">
		 <h6 class="fw-normal">
Thời gian: <span class="pull-right">${phimListHot[0].getThoiLuong()}m</span></h6>
         <hr class="hr_1">
		  <h6 class="fw-normal">
Thể loại: <span class="pull-right">							    <c:if test="${not empty phimListHot[0].chiTietTheLoais}">
							        <c:forEach var="chiTiet" items="${phimListHot[0].chiTietTheLoais}" varStatus="status">
							            <c:out value="${chiTiet.theLoai.tenTL}" />
							            <c:if test="${!status.last}">, </c:if>
							        </c:forEach>
							    </c:if></span></h6>
         <hr class="hr_1">
		 <h6 class="fw-normal">
Đạo diễn: <span class="pull-right">${phimListHot[0].getDaoDien().getTenDaoDien()}</span></h6>
         <hr class="hr_1">
		  <h6 class="fw-normal">
Diễn viên: <span class="pull-right">						    <c:if test="${not empty phimListHot[0].vaiDiens}">
							        <c:forEach var="chiTietdienvien" items="${phimListHot[0].vaiDiens}" varStatus="status">
							            <c:out value="${chiTietdienvien.getDienVien().tenDienVien}" />
							            <c:if test="${!status.last}">, </c:if>
							        </c:forEach>
							    </c:if></span></h6>
         <hr class="hr_1">
		 <h6 class="fw-normal">
Năm sản xuất: <span class="pull-right">${phimListHot[0].getNamSX()}</span></h6>
         <hr class="hr_1 mb-0">
		</div>
	   </div>
	   <div class="col-md-6">
	    <div class="play2rir">
		 <h6 class="fw-normal">Điểm đánh giá của khách hàng: <span class="col_red"><b>${phimListHot[0].getDiem()}</b></span> / 5 <span class="col_red"><i class="fa fa-star"></i></span></h6>
         <div class="progress">
  <div class="progress-bar" role="progressbar" style="width: 100%;" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
</div>
          <h6 class="fw-normal mt-3">Đánh giá 5 <span class="col_red"><i class="fa fa-star"></i></span> - ${thongKeDanhGiaMap[phimListHot[0].maPhim][4]} khách hàng</h6>
         <div class="progress">
  <div class="progress-bar" role="progressbar" style="width: 100%;" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
</div>
          <h6 class="fw-normal mt-3">Đánh giá 4 <span class="col_red"><i class="fa fa-star"></i></span> - ${thongKeDanhGiaMap[phimListHot[0].maPhim][3]} khách hàng</h6>
         <div class="progress">
  <div class="progress-bar" role="progressbar" style="width: 80%;" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
</div>
          <h6 class="fw-normal mt-3">Đánh giá 3 <span class="col_red"><i class="fa fa-star"></i></span> - ${thongKeDanhGiaMap[phimListHot[0].maPhim][2]} khách hàng</h6>
         <div class="progress">
  <div class="progress-bar" role="progressbar" style="width: 60%;" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
</div>
        <h6 class="fw-normal mt-3">Đánh giá 2 <span class="col_red"><i class="fa fa-star"></i></span> - ${thongKeDanhGiaMap[phimListHot[0].maPhim][1]} khách hàng</h6>
         <div class="progress">
  <div class="progress-bar" role="progressbar" style="width: 40%;" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
</div>
		</div>
	   </div>
	  </div>
   </div>
  </div>
 </div>
</div>
</div>
</section>

<section id="choice" class="pt-4 pb-5">
	<div class="container">
 		<div class="row trend_1">
  			<div class="col-md-6 col-6">
   				<div class="trend_1l">
    				<h4 class="mb-0">
    					<i class="fa fa-youtube-play align-middle col_red me-1"></i> 
    					Đạo diễn  <span class="col_red">nghìn tỉ</span>
    				</h4>
   				</div>
  			</div>
 		</div>
 		<div class="row trend_2 mt-4">
   			<div id="carouselExampleCaptions3" class="carousel slide" data-bs-ride="carousel">
  				<div class="carousel-indicators">
    				<button type="button" data-bs-target="#carouselExampleCaptions3" data-bs-slide-to="0" class="active" aria-label="Slide 1"></button>
    				<button type="button" data-bs-target="#carouselExampleCaptions3" data-bs-slide-to="1" aria-label="Slide 2"></button>
  				</div>
  				<div class="carousel-inner">
			        <c:forEach var="daodien" items="${DaoDienList}" varStatus="status" begin="0" end="5">
			            <c:if test="${status.index % 3 == 0}">
                    		<div class="carousel-item ${status.index == 0 ? 'active' : ''}">
                        		<div class="row trend_2i"> <!-- Đảm bảo sử dụng đúng hàng -->
			            </c:if>
								<div class="col-md-4">
			  						<div class="trend_2im clearfix position-relative">
		   								<div class="trend_2im1 clearfix">
		   		 							<div class="grid">
		  										<figure class="effect-jazz mb-0">
													<a href="#"><img src="${daodien.getAnh()}" class="w-100" alt="img25"></a>
	  											</figure>
	  										</div>
	   									</div>
			   							<div class="trend_2im2 clearfix position-absolute w-100 top-0">
			     							<h5><a class="col_red" href="#">${daodien.getTenDaoDien()}</a></h5>
											<span class="col_red">
												<i class="fa fa-star"></i>
												<i class="fa fa-star"></i>
												<i class="fa fa-star"></i>
												<i class="fa fa-star"></i>
												<i class="fa fa-star"></i>
											</span>
			   							</div>
	  								 </div>
	 								</div>
			 				<c:if test="${status.index % 3 == 2 || status.last}">
				                </div> <!-- Đóng hàng ở đây -->
				            </div> <!-- Đóng carousel-item ở đây -->
				        </c:if>
				    </c:forEach>
  				</div>
			</div>
		</div>
	</div>
</section>

<section id="footer">
 <div class="footer_m clearfix">
  <div class="container">
   <div class="row footer_1">
    <div class="col-md-4">
     <div class="footer_1i">
      <h3><a class="text-white" href="lienhe.htm"><i class="fa fa-video-camera col_red me-1"></i> <span class="col_red">Liên hệ</span></a></h3>
       <p class="mt-3">Tứ Trụ. Nơi hội tụ cảm xúc.</p>
       <h6 class="fw-normal"><i class="fa fa-map-marker fs-5 align-middle col_red me-1"></i>97 Đ. Man Thiện, Hiệp Phú, Thủ Đức, Thành phố Hồ Chí Minh, Vietnam</h6>
       <h6 class="fw-normal mt-3"><i class="fa fa-envelope fs-5 align-middle col_red me-1"></i> tutrucinema@gmail.com</h6>
       <h6 class="fw-normal mt-3 mb-0"><i class="fa fa-phone fs-5 align-middle col_red me-1"></i>  1900 0099</h6>
     </div>
    </div>
   <div class="col-md-4">
    <div class="footer_1i">
     <h4><span class="col_red">Bản tin</span></h4>
      <p class="mt-3">Đăng ký danh sách bản tin của chúng tôi để nhận tin tức và cập nhật mới nhất từ chúng tôi</p>
       <div class="input-group">
        <input type="text" class="form-control bg-black" placeholder="Email">
         <span class="input-group-btn">
          <button class="btn btn text-white bg_red rounded-0 border-0" type="button">Đăng kí</button>
         </span>
        </div>
       </div>
      </div>
  <div class="col-md-4">
    <div class="footer_1i text-center">
        <h4 class="mb-4"> <span class="col_red">Kết nối với chúng tôi</span></h4>
        <div class="footer_1i1">
            <ul class="social-network social-square mb-0 mt-4">
                <li class=""><a href="https://www.facebook.com/profile.php?id=100018102729044"><img  src="images/icon-facebook.png" height="50" title="Facebook" alt="Facebook"></a></li>
                <li class=""><a href=""><img  src="images/icon-instagram.png" height="50" title="Instagram" alt="Instagram"></a></li>
                <li class=""><a href=""><img  src="images/icon-tiktok.png" height="50" title="Tiktok" alt="Tiktok"></a></li>
                <li class=""><a href=""><img  src="images/icon-youtube.png" height="50" title="Youtube" alt="Youtube"></a></li>
            </ul>
        </div>
        <div class="footer_1i1 mt-3">
            <a href="http://online.gov.vn/HomePage/CustomWebsiteDisplay.aspx?DocId=30278" target="_blank">
                <img src="images/icon-bocongthuong.png" height="80" alt="abc">
            </a>
        </div>
       </div>
      </div>
     </div>
    </div>
   </div>
</section>

<section id="footer_b" class="pt-3 pb-3 bg_grey">
<div class="container">
   <div class="row footer_1 text-center">
	   <p class="mb-0">© 2024 Tu Tru Cinema. All Rights Reserved</p>
   </div>
</div>
</section>

<!-- Chatbox Icon -->
<div class="chatbox-icon" onclick="openModal()">
	<div id="greeting" class="greeting-message">
	    <b>AI Tứ Trụ luôn sẵn sàng hỗ trợ!</b>
	</div>
    <div class="chat-icon">
        <img src="/DoAnWebCinema/images/chatboxicon.gif" alt="Chat Icon">
    </div>
</div>

<!-- Modal -->
<div id="chatModal" class="modal">
    <!-- Modal Content -->
    <div class="modal-content">
		<div class="modal-header">
		    <div class="header-left">
		        <img src="http://localhost:9999/DoAnWebCinema/images/logocinema.png" alt="Tứ Trụ Cinema Logo" class="logo-icon" />
		        <div class="brand-text">
		            <span class="cinema-name"><b>Tứ Trụ Cinema</b></span>
		            <span class="subtitle">Hỗ trợ khách hàng trực tuyến</span>
		        </div>
		    </div>
		    <button class="close-btn" onclick="closeModal()">&times;</button>
		</div>
<!-- 		<div class="scrolling-message">
            <p style="color: black;">Chào mừng quý khách đến với hệ thống hỗ trợ, chúng tôi luôn sẵn sàng giải đáp thắc mắc của bạn</p>
        </div> -->
        <div class="modal-body">
            <div id="chat-content">
                <!-- Dynamic chat messages will be appended here -->
            </div>
        </div>
        <div class="modal-footer">
            <input type="text" id="chat-input" placeholder="Nhập tin nhắn..." />
            <input type="hidden" id="user-id" value="${user.maKH}">
            <button id="send-btn">Gửi</button>
        </div>
    </div>
</div>

<script>
//Mở modal chat
function openModal() {
    document.getElementById('chatModal').style.display = 'flex';
}

// Đóng modal chat
function closeModal() {
    document.getElementById('chatModal').style.display = 'none';
}

//Danh sách gợi ý ngẫu nhiên
const suggestions = {
    notLoggedIn: [
        { text: "Phim <b><c:out value='${phimBanChayNhat[1]}'/></b> đang hot, bạn có muốn đặt vé ngay không?", tag: "Tôi muốn đặt vé xem phim <c:out value="${phimBanChayNhat[1] != null ? phimBanChayNhat[1].toLowerCase() : ''}" />" },
        { text: "Bạn cần giúp đỡ gì không!", tag: "Hỗ trợ khách hàng" },
        { text: "Bạn đã sẵn sàng cho trải nghiệm rạp phim tuyệt vời?", tag: "Gợi ý phim hot" },
    ],
    loggedIn: [
        { text: "Nếu bạn chưa biết xem phim gì thì tôi sẽ hỗ trợ cho bạn nhé!", tag: "Gợi ý phim" },
        { text: "Bạn có muốn xem đề xuất của tôi dành cho bạn không?", tag: "Gợi ý phim" },
        { text: "Có thể bạn sẽ yêu thích, xem gợi ý của tôi nhé!", tag: "Gợi ý phim" },
        { text: "Phim <b><c:out value='${phimBanChayNhat[1]}'/></b> đang hot, bạn có muốn đặt vé ngay không?", tag: "Tôi muốn đặt vé xem phim <c:out value="${phimBanChayNhat[1] != null ? phimBanChayNhat[1].toLowerCase() : ''}" />" }
    ]
};

let usedTags = [];

// Kiểm tra trạng thái đăng nhập
function isLoggedIn() {
    const userId = document.getElementById('user-id').value;
    return userId !== ""; // Giả sử userId rỗng nghĩa là chưa đăng nhập
}

//Lấy một gợi ý không trùng lặp
function getRandomSuggestion(isUserLoggedIn) {
    const pool = isUserLoggedIn ? suggestions.loggedIn : suggestions.notLoggedIn;

    // Lọc ra các gợi ý chưa sử dụng
    const availableSuggestions = pool.filter(suggestion => !usedTags.includes(suggestion.tag));

    // Nếu không còn gợi ý mới, làm mới danh sách đã sử dụng
    if (availableSuggestions.length === 0) {
        usedTags = [];
        return getRandomSuggestion(isUserLoggedIn);
    }

    // Chọn ngẫu nhiên một gợi ý từ danh sách còn lại
    const randomIndex = Math.floor(Math.random() * availableSuggestions.length);
    const selectedSuggestion = availableSuggestions[randomIndex];

    // Thêm tag vào danh sách đã sử dụng
    usedTags.push(selectedSuggestion.tag);

    return selectedSuggestion;
}

//Tạo gợi ý và hiển thị trong 15 giây
function displaySuggestion() {
    const isUserLoggedIn = isLoggedIn();
    const randomSuggestion = isUserLoggedIn
        ? suggestions.loggedIn[Math.floor(Math.random() * suggestions.loggedIn.length)]
        : suggestions.notLoggedIn[Math.floor(Math.random() * suggestions.notLoggedIn.length)];

    const greetingElement = document.getElementById('greeting');

    // Hiển thị gợi ý
    greetingElement.innerHTML = randomSuggestion.text;
    greetingElement.classList.add('show'); // Thêm class "show" để hiển thị
	
    greetingElement.onclick = function () {
    	 sendMessage(randomSuggestion.tag);
    };
    
    // Ẩn sau 15 giây
    setTimeout(() => {
        greetingElement.classList.remove('show'); // Gỡ class "show" để ẩn
    }, 15000); // 15 giây
}

// Lặp lại hiển thị gợi ý sau mỗi 3 phút
setInterval(displaySuggestion, 30000); // 3 phút
displaySuggestion(); // Hiển thị ngay lần đầu tiên


function sendMessage(tag) {
    const input = document.getElementById('chat-input');
    const message = tag ? tag : input.value.trim(); 
    const userId = document.getElementById('user-id').value; // Lấy user_id từ hidden input

    if (message) {
        const chatContent = document.getElementById('chat-content');

        // Hiển thị tin nhắn của người dùng
        const userMessage = document.createElement('div');
        userMessage.classList.add('chat-message', 'user-message');

        const messageContent = document.createElement('div');
        messageContent.classList.add('message-content');
        messageContent.textContent = message;

        userMessage.appendChild(messageContent);
        chatContent.appendChild(userMessage);

        // Tự động cuộn xuống dưới
        chatContent.scrollTop = chatContent.scrollHeight;

        input.value = '';

        // Gửi tin nhắn cùng user_id đến chatbot Python qua API Flask
        fetch('http://127.0.0.1:5000/handle_message', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ message: message, user_id: userId }),
        })
            .then(response => response.json())
            .then(data => {
                // Hiển thị phản hồi từ chatbot
                const botMessage = document.createElement('div');
                botMessage.classList.add('chat-message', 'bot-message');

                const botMessageContent = document.createElement('div');
                botMessageContent.classList.add('message-content');
                botMessageContent.innerHTML = data.response;

                botMessage.appendChild(botMessageContent);
                chatContent.appendChild(botMessage);

                // Tự động cuộn xuống dưới
                chatContent.scrollTop = chatContent.scrollHeight;
            })
            .catch(error => {
                console.error('Lỗi khi gọi API Flask:', error);
            });
    }
}


// Sự kiện nhấn nút Gửi
document.getElementById('send-btn').addEventListener('click', sendMessage);

// Sự kiện nhấn phím Enter
document.getElementById('chat-input').addEventListener('keydown', function (event) {
    if (event.key === 'Enter') {
        event.preventDefault(); // Ngăn hành vi mặc định
        sendMessage();
    }
});

</script>

<script>
window.onscroll = function() {myFunction()};

var navbar_sticky = document.getElementById("navbar_sticky");
var sticky = navbar_sticky.offsetTop;
var navbar_height = document.querySelector('.navbar').offsetHeight;

function myFunction() {
  if (window.pageYOffset >= sticky + navbar_height) {
    navbar_sticky.classList.add("sticky")
	document.body.style.paddingTop = navbar_height + 'px';
  } else {
    navbar_sticky.classList.remove("sticky");
	document.body.style.paddingTop = '0'
  }
}

function openLightbox(linkTrailer, lightboxId, videoId) {
    document.getElementById(lightboxId).style.display = 'flex';  // Hiển thị lightbox
    document.getElementById(videoId).src = linkTrailer.replace("watch?v=", "embed/") + "?autoplay=1&mute=1";  // Nhúng video YouTube
}

function closeLightbox(lightboxId, videoId) {
    document.getElementById(lightboxId).style.display = 'none';  // Ẩn lightbox
    document.getElementById(videoId).src = '';  // Dừng video bằng cách xóa src
}


</script>
<script>
    // Lấy link trailer từ database (ở đây là từ phimListHot)
    var originalLink = "${phimListHot[0].getLinkTrailer()}"; // Ví dụ: https://www.youtube.com/watch?v=dQw4w9WgXcQ

    // Chuyển đổi link thành dạng nhúng
    var embedLink = originalLink.replace("watch?v=", "embed/");

    // Thiết lập lại link cho iframe
    document.getElementById("videoID").src = embedLink + "?autoplay=1&mute=1";
</script>


<script>
    // Lấy ngày hiện tại
    var curDate = new Date();
    var date = curDate.getDate();
    var month = curDate.getMonth() + 1; // Tháng bắt đầu từ 0
    var year = curDate.getFullYear();

    // Định dạng ngày theo định dạng yyyy-mm-dd
    var formattedDate = year + '-' + (month < 10 ? '0' + month : month) + '-' + (date < 10 ? '0' + date : date);

    // Cập nhật liên kết href
    var link = document.getElementById('dat-ve-link');
    if (link) { // Kiểm tra xem phần tử có tồn tại không
        link.href = '/DoAnWebCinema/datve/' + formattedDate + '.htm?chinhanh=1';
    }
</script>

<script>
    document.addEventListener("DOMContentLoaded", function() {
        // Lấy tất cả các mục trong menu
        const menuItems = document.querySelectorAll('.navbar-nav .nav-item a');
        
        // Lấy đường dẫn của trang hiện tại
        const currentPath = window.location.pathname;

        // Duyệt qua từng mục menu
        menuItems.forEach(function(item) {
            // Kiểm tra nếu href của mục menu trùng với đường dẫn hiện tại
            if (item.getAttribute('href') === currentPath) {
                // Thêm lớp active vào mục menu
                item.classList.add('active');
            } else {
                // Loại bỏ lớp active khỏi các mục menu khác
                item.classList.remove('active');
            }
        });
    });
</script>

</body>

</html>