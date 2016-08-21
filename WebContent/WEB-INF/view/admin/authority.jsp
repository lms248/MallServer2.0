<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

 <!-- page heading start-->
        <div class="page-heading">
            <h3>
                Media Gallery
            </h3>
            <ul class="breadcrumb">
                <li>
                    <a href="#">Dashboard</a>
                </li>
                <li class="active"> Media Gallery </li>
            </ul>
        </div>
        <!-- page heading end-->

        <!--body wrapper start-->
        <div class="wrapper">
            <div class="row">
                <div class="col-sm-12">
                    <section class="panel">
                        <header class="panel-heading">
                            Media Manager
                            <span class="tools pull-right">
                                <a href="javascript:;" class="fa fa-chevron-down"></a>
                                <a href="javascript:;" class="fa fa-times"></a>
                             </span>
                        </header>
                        <div class="panel-body">

                            <ul id="filters" class="media-filter">
                                <li><a href="#" data-filter="*"> All</a></li>
                                <li><a href="#" data-filter=".images">Images</a></li>
                                <li><a href="#" data-filter=".audio">Audio</a></li>
                                <li><a href="#" data-filter=".video">Video</a></li>
                                <li><a href="#" data-filter=".documents">Documents</a></li>
                            </ul>

                            <div class="btn-group pull-right">
                                <button type="button" class="btn btn-primary btn-sm"><i class="fa fa-check-square-o"></i> Select all</button>
                                <button type="button" class="btn btn-primary btn-sm"><i class="fa fa-folder-open"></i> Add New</button>
                                <button type="button" class="btn btn-primary btn-sm"><i class="fa fa-trash-o"></i> Delete</button>
                                <button type="button" class="btn btn-primary btn-sm"><i class="fa fa-upload"></i> Upload New File</button>
                            </div>



                            <div id="gallery" class="media-gal">
                                <div class="images item " >
                                    <a href="#myModal" data-toggle="modal">
                                        <img src="/res/img/user1.png" alt="" />
                                    </a>
                                    <p>user1.png </p>
                                </div>
                            </div>

                            <div class="col-md-12 text-center clearfix">
                                <ul class="pagination">
                                    <li><a href="#">«</a></li>
                                    <li><a href="#">1</a></li>
                                    <li><a href="#">2</a></li>
                                    <li><a href="#">3</a></li>
                                    <li><a href="#">4</a></li>
                                    <li><a href="#">5</a></li>
                                    <li><a href="#">»</a></li>
                                </ul>
                            </div>

                            <!-- Modal -->
                            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                            <h4 class="modal-title">Edit Media Gallery</h4>
                                        </div>

                                        <div class="modal-body row">

                                            <div class="col-md-5 img-modal">
                                                <img src="/res/img/user1.png" alt="">
                                                <a href="#" class="btn btn-white btn-sm"><i class="fa fa-pencil"></i> Edit Image</a>
                                                <a href="#" class="btn btn-white btn-sm"><i class="fa fa-eye"></i> View Full Size</a>

                                                <p class="mtop10"><strong>File Name:</strong> img01.jpg</p>
                                                <p><strong>File Type:</strong> jpg</p>
                                                <p><strong>Resolution:</strong> 300x200</p>
                                                <p><strong>Uploaded By:</strong> <a href="#">ThemeBucket</a></p>
                                            </div>
                                            <div class="col-md-7">
                                                <div class="form-group">
                                                    <label> Name</label>
                                                    <input id="name" value="img01.jpg" class="form-control">
                                                </div>
                                                <div class="form-group">
                                                    <label> Tittle Text</label>
                                                    <input id="title" value="awesome image" class="form-control">
                                                </div>
                                                <div class="form-group">
                                                    <label> Description</label>
                                                    <textarea rows="2" class="form-control"></textarea>
                                                </div>
                                                <div class="form-group">
                                                    <label> Link URL</label>
                                                    <input id="link" value="/res/img/user1.png" class="form-control">
                                                </div>
                                                <div class="pull-right">
                                                    <button class="btn btn-danger btn-sm" type="button">Delete</button>
                                                    <button class="btn btn-success btn-sm" type="button">Save changes</button>
                                                </div>
                                            </div>

                                        </div>

                                    </div>
                                </div>
                            </div>
                            <!-- modal -->

                        </div>
                    </section>
                </div>
            </div>
        </div>
        <!--body wrapper end-->
<!-- Placed js at the end of the document so the pages load faster -->
<!-- <script src="js/jquery-1.10.2.min.js"></script>
<script src="js/jquery-ui-1.9.2.custom.min.js"></script>
<script src="js/jquery-migrate-1.2.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/modernizr.min.js"></script>
<script src="js/jquery.nicescroll.js"></script>

<script src="js/jquery.isotope.js"></script> -->

<!--common scripts for all pages-->
<!-- <script src="js/scripts.js"></script> -->

<script type="text/javascript">
    $(function() {
        var $container = $('#gallery');
        $container.isotope({
            itemSelector: '.item',
            animationOptions: {
                duration: 750,
                easing: 'linear',
                queue: false
            }
        });

        // filter items when filter link is clicked
        $('#filters a').click(function() {
            var selector = $(this).attr('data-filter');
            $container.isotope({filter: selector});
            return false;
        });
    });
</script>

