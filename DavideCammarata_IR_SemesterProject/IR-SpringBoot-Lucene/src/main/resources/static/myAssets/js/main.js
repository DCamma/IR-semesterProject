var first = true;

document.addEventListener("keydown", keyDownTextField, false);

function keyDownTextField(e) {
    var keyCode = e.keyCode;
    if(keyCode==13) {
        searchFromQuery();
    }
}

function searchFromQuery(){
    var query = $("#queryText");
    console.log(query.val());
    $.post("/api/v1/query", {
        query: query.val()
    }).done(successSearch).fail(failedSearch)
}

function successSearch(data) {
    console.log(data);
    if (first) dust.render('header', null, partialRender);
    if (first) $("#queryText").val(data.query);
    dust.render('results', data, renderResults);
}

function failedSearch(data) {
    console.log(data);
    console.log(JSON.parse(data.responseText).errorMessage)
    if (first) notify(JSON.parse(data.responseText).errorMessage, "error", "2000", "bottom", "center")
    if (!first) dust.render('results', null, renderResults);
}

function partialRender(err, out) {
    $("#content-window").html(out)
}
function renderResults(err, out) {
    $("#resultsSpace").html(out)
    if (first) document.getElementById("queryText").addEventListener("keyup", searchFromQuery)
    first = false;
}


