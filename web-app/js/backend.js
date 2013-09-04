
var postCount = 0;
var refreshLink = "";

function getAllPostsForTeam() {
    $.ajax({
        type: "GET",
        dataType: "json",
        url: refreshLink,
        success: function(data){
            $.each(data.sins, function (i, theItem) {
                createPostBefore(theItem.sinner, theItem.sin, 'images/stock/github.png');
            });
            setRefreshLink(data.refreshLink);
        }
    });
}

function setRefreshLink(link) {
    refreshLink = link;
}

function createPostAfter(sinner, sin, image) {
    var postList = document.getElementById("post-list");
    postList.appendChild(__createPostInternal(sinner, sin, image));
    setNewColors(postList);
    $('div.post').corner();
}

function createPostBefore(sinner, sin, image) {
    var postList = document.getElementById("post-list");
    postList.insertBefore(__createPostInternal(sinner, sin, image), postList.firstChild);
    setNewColors(postList);
    $('div.post').corner();
}

function setNewColors(postList) {
    var childNodes = postList.childNodes;
    var postCount = 0;
    for(var i = 0; i < childNodes.length; i++) {
        if(childNodes[i].nodeType != 1) //ELEMENT NODE
            continue;
        var newColor = "#" + Math.max(255 - postCount * 5, 0).toString(16) + "0000";
        postCount = postCount + 1;
        console.log(i + "->" + postCount + ":" + newColor);
        childNodes[i].style.borderColor = newColor;
    }
}

function __createPostInternal(sinner, sin, image) {
    var postDiv = document.createElement('div');
    postDiv.className = 'post';
    postDiv.appendChild(createImagePost(image, sinner));
    postDiv.appendChild(createSinBody(sin));
    postDiv.appendChild(createSinner(sinner));
    return postDiv
}

function createImagePost(image, sinner) {
    var imageDiv = document.createElement('div');
    imageDiv.className = 'author-icon';
    var imageTag = document.createElement('img');
    imageTag.src = image;
    imageTag.alt = sinner;
    imageDiv.appendChild(imageTag)
    return imageDiv;
}

function createSinBody(sin) {
    var sinDiv = document.createElement('div');
    sinDiv.className = 'body';
    var sinNode = document.createTextNode(sin);
    sinDiv.appendChild(sinNode)
    return sinDiv;
}

function createSinner(sinner) {
    var sinnerDiv = document.createElement('div');
    sinnerDiv.className = 'sinner';
    var sinnerName = document.createTextNode("Sinner: " + sinner);
    sinnerDiv.appendChild(sinnerName)
    return sinnerDiv;
}