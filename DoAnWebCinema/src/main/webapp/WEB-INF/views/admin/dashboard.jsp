<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html dir="ltr" lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- Tell the browser to be responsive to screen width -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="keywords"
        content="wrappixel, admin dashboard, html css dashboard, web dashboard, bootstrap 5 admin, bootstrap 5, css3 dashboard, bootstrap 5 dashboard, Ample lite admin bootstrap 5 dashboard, frontend, responsive bootstrap 5 admin template, Ample admin lite dashboard bootstrap 5 dashboard template">
    <meta name="description"
        content="Ample Admin Lite is powerful and clean admin dashboard template, inpired from Bootstrap Framework">
    <meta name="robots" content="noindex,nofollow">
    <title>Thống Kê</title>
     <link rel="canonical" href="https://www.wrappixel.com/templates/ample-admin-lite/" />
    <link rel="icon" type="image/png" sizes="16x16" href="http://localhost:9999/DoAnWebCinema/imgs/favicon.png">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link href="http://localhost:9999/DoAnWebCinema/css/chartist.min.css" rel="stylesheet">
    <link rel="stylesheet" href="http://localhost:9999/DoAnWebCinema/css/chartist-plugin-tooltip.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link href="http://localhost:9999/DoAnWebCinema/css/style.min.css" rel="stylesheet">
    <link href="http://localhost:9999/DoAnWebCinema/css/employee.css" rel="stylesheet">
    <link rel="stylesheet" href="http://localhost:9999/DoAnWebCinema/css/detail-test.css">
    <link rel="stylesheet" href="http://localhost:9999/DoAnWebCinema/css/fix-test.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.22/css/dataTables.bootstrap4.min.css">
    <script src="https://cdn.datatables.net/1.10.22/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.22/js/dataTables.bootstrap4.min.js"></script>
    <link rel="stylesheet" href="<c:url value='/css/alertify.min.css'/>" />
</head>

<body>
    <div class="preloader">
        <div class="lds-ripple">
            <div class="lds-pos"></div>
            <div class="lds-pos"></div>
        </div>
    </div>
    <div id="main-wrapper" data-layout="vertical" data-navbarbg="skin5" data-sidebartype="full"
        data-sidebar-position="absolute" data-header-position="absolute" data-boxed-layout="full">
		<%@include file="/WEB-INF/views/include/header_admin.jsp"%> 
        <%@include file="/WEB-INF/views/include/aside.jsp"%>
        <div class="page-wrapper">
            <div class="page-breadcrumb bg-white">
                <div class="row align-items-center">
                    <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                        <h4 class="page-title">Thống Kê</h4>
                    </div>
                    
                </div>
            </div>
            <div class="container-fluid">
                <div class="row justify-content-center">
                    <div class="col-lg-3 col-md-12">
                        <div class="white-box analytics-info">
                            <h3 class="box-title">Người Sử Dụng</h3>
                            <ul class="list-inline two-part d-flex align-items-center mb-0">
                                <li>
                                    <div id="sparklinedash"><canvas width="67" height="30"
                                            style="display: inline-block; width: 67px; height: 30px; vertical-align: top;"></canvas>
                                    </div>
                                </li>
                                <li class="ms-auto"><span class="counter text-success">${countUser}</span></li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-12">
                        <div class="white-box analytics-info">
                            <h3 class="box-title">Vé Bán Ra</h3>
                            <ul class="list-inline two-part d-flex align-items-center mb-0">
                                <li>
                                    <div id="sparklinedash2"><canvas width="67" height="30"
                                            style="display: inline-block; width: 67px; height: 30px; vertical-align: top;"></canvas>
                                    </div>
                                </li>
                                <li class="ms-auto"><span class="counter text-purple">${countVe}</span></li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-12">
                        <div class="white-box analytics-info">
                            <h3 class="box-title">Tổng số phim</h3>
                            <ul class="list-inline two-part d-flex align-items-center mb-0">
                                <li>
                                    <div id="sparklinedash3"><canvas width="67" height="30"
                                            style="display: inline-block; width: 67px; height: 30px; vertical-align: top;"></canvas>
                                    </div>
                                </li>
                                <li class="ms-auto"><span class="counter text-info">${countPhim}</span>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-12">
                        <div class="white-box analytics-info">
                            <h3 class="box-title">Tổng doanh thu</h3>
                            <ul class="list-inline two-part d-flex align-items-center mb-0">
                                <li>
                                    <div id="sparklinedash"><canvas width="67" height="30"
                                            style="display: inline-block; width: 67px; height: 30px; vertical-align: top;"></canvas>
                                    </div>
                                </li>
                                <li class="ms-auto"><span class="counter text-success"><fmt:formatNumber value="${tongDoanhThu}" pattern="#,##0.###" /></span></li>
                            </ul>
                        </div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-md-12 col-lg-12 col-sm-12">
                        <div class="white-box">
                            <div class="d-md-flex mb-3">
                                <h3 class="box-title mb-0">Danh Sách Phim Hot</h3>
                                <div class="col-md-3 col-sm-4 col-xs-6 ms-auto">
								    <select class="form-select shadow-none row border-top" id="monthSelect" onchange="filterByMonth()">
								        <option value="">Chọn tháng</option>
								    </select>
								</div>
                            </div>
                            <div class="table-responsive">
                                <table class="table no-wrap">
                                    <thead>
                                        <tr>
                                            <th class="border-top-0">STT</th>
                                            <th class="border-top-0">Phim</th>
                                            <th class="border-top-0">Doanh Thu</th>
                                            <th class="border-top-0">Trạng Thái</th>
                                            <th class="border-top-0">Ngày chiếu</th>
                                            <th class="border-top-0">Thời lượng</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="p" items="${phim}" varStatus="loop">
                                        <tr>
                                            <td>${loop.index + 1}</td>
                                            <td class="txt-oflo">${p.tenPhim }</td>
                                            <td class="txt-oflo"><fmt:formatNumber value="${doanhThuTheoPhim[p.maPhim]}" pattern="#,##0.###" /></td>
                                            <td>
                                            	<c:choose>
                                            		<c:when test="${p.maTT==1}">
											            <span class="col_red me-3">Đang chiếu</span>
											        </c:when>
											        <c:when test="${p.maTT==2}">
											            <span class="col_red me-3">Sắp chiếu</span>
											        </c:when>
											        <c:when test="${p.maTT==0}">
											            <span class="col_red me-3">Ngừng chiếu</span>
											        </c:when>
                                            	</c:choose>
                                            </td>
                                            <td class="txt-oflo">
											    <fmt:formatDate value="${p.ngayKhoiChieu}" pattern="dd-MM-yyyy" />
											</td>

                                            <td><span class="text-success">${p.thoiLuong } phút</span></td>
                                        </tr>
                                       
                                       </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                
                    
            <!-- ============================================================== -->
            <!-- End Container fluid  -->
            <!-- ============================================================== -->
            <!-- ============================================================== -->
            <!-- footer -->
            <!-- ============================================================== -->
            <!-- ============================================================== -->
            <!-- End footer -->
            <!-- ============================================================== -->
        </div>
        <!-- ============================================================== -->
        <!-- End Page wrapper  -->
        <!-- ============================================================== -->
    </div>
    </div>
    <!-- ============================================================== -->
    <!-- End Wrapper -->
    <!-- ============================================================== -->
    <!-- ============================================================== -->
    <!-- All Jquery -->
    <!-- ============================================================== -->
    <script src="http://localhost:9999/DoAnWebCinema/js/jquery.min.js"></script>
    <!-- Bootstrap tether Core JavaScript -->
    <script src="http://localhost:9999/DoAnWebCinema/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
    <script src="http://localhost:9999/DoAnWebCinema/js/app-style-switcher.js"></script>
    <script src="http://localhost:9999/DoAnWebCinema/js/jquery.sparkline.min.js"></script>
    <!--Wave Effects -->
    <script src="http://localhost:9999/DoAnWebCinema/js/waves.js"></script>
    <!--Menu sidebar -->
    <script src="http://localhost:9999/DoAnWebCinema/js/sidebarmenu.js"></script>
    <!--Custom JavaScript -->
    <script src="http://localhost:9999/DoAnWebCinema/js/custom.js"></script>
    <!--This page JavaScript -->
    <!--chartis chart-->
    <script src="http://localhost:9999/DoAnWebCinema/js/chartist.min.js"></script>
    <script src="http://localhost:9999/DoAnWebCinema/js/chartist-plugin-tooltip.min.js"></script>
    <script src="http://localhost:9999/DoAnWebCinema/js/dashboard1.js"></script>
<script>
    const currentYear = new Date().getFullYear();
    const currentMonth = new Date().getMonth() + 1;
    const monthSelect = document.getElementById("monthSelect");

    for (let month = 1; month <= 12; month++) {
        const option = document.createElement("option");
        option.value = month;
        option.textContent = 'Tháng ' + month + ' năm ' + currentYear;
        
        if (month === currentMonth) {
            option.selected = true;
        }
        
        monthSelect.appendChild(option);
    }

    const urlParams = new URLSearchParams(window.location.search);
    const selectedMonthFromURL = urlParams.get('month');
    if (selectedMonthFromURL) {
        monthSelect.value = selectedMonthFromURL;
    }

    function filterByMonth() {
        const selectedMonth = document.getElementById("monthSelect").value;
        console.log("Selected Month:", selectedMonth);
        if (selectedMonth) {
            const url = '/DoAnWebCinema/admin/dashboard.htm?month=' + selectedMonth + '&year=' + currentYear;
            console.log("URL:", url);
            window.location.href = url;
        } else {
            console.log("No month selected!");
        }
    }
</script>
</body>

</html>