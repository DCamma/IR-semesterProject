// views/header.dust
(function(){dust.register("header",body_0);function body_0(chk,ctx){return chk.w("<div class=\"panel\"><div class=\"panel-body\"><ul id=\"tabs-demo6\" class=\"nav nav-tabs nav-tabs-v6 masonry-tabs\" role=\"tablist\"><div class=\"paddingHeader animated fadeIn form-group form-animate-text\"><div class=\"col-md-10\"><input id=\"queryText\" class=\"form-text\" required=\"\" aria-required=\"true\" type=\"text\" autofocus><span class=\"bar\"></span><label style=\"padding-left: 10px;\">Enter your query</label></div><div class=\"col-md-2\"><input class=\"btn btn-gradient btn-primary margin-top-20\" value=\"Search\" type=\"button\"></div></div></ul></div></div><div id=\"resultsSpace\" class=\"col-md-12\"></div>");}body_0.__dustBody=!0;return body_0;})();
 // views/results.dust
(function(){dust.register("results",body_0);function body_0(chk,ctx){return chk.x(ctx.get(["count"], false),ctx,{"block":body_1},{});}body_0.__dustBody=!0;function body_1(chk,ctx){return chk.w("<div class=\"col-md-12 tabs-area box-shadow-none\"><div id=\"tabsDemo6Content\" class=\"tab-content tab-content-v6 col-md-12\"><div role=\"tabpanel\" class=\"tab-pane search-v1-menu1 fade active in\" id=\"tabs-demo7-area1\" aria-labelledby=\"tabs-demo7-area1\"><h4 class=\"\"> ").f(ctx.get(["count"], false),ctx,"h").w(" results found for: “").f(ctx.get(["query"], false),ctx,"h").w("”</h4><br><br><div class=\"col-md-8 padding-0\">").s(ctx.get(["results"], false),ctx,{"block":body_2},{}).w("</div></div></div></div>");}body_1.__dustBody=!0;function body_2(chk,ctx){return chk.w("<div class=\" media\"><div class=\"col-md-2 padding-0 scoreCol\"><div class=\"media-left\">").f(ctx.get(["score"], false),ctx,"h").w(" <small>SCORE</small></div></div><div class=\"col-md-10 padding-0\"><div class=\"media-body\"><h2><a class=\"resultTitle media-heading\" href=\"").f(ctx.get(["url"], false),ctx,"h").w("\">").f(ctx.get(["title"], false),ctx,"h").w("</a></h2><p class=\"resDescription\">").f(ctx.get(["content"], false),ctx,"h").w("</p><a href=\"").f(ctx.get(["url"], false),ctx,"h").w("\"><span class=\"label label-default\">").f(ctx.get(["url"], false),ctx,"h").w("</span></a></div></div></div>");}body_2.__dustBody=!0;return body_0;})();