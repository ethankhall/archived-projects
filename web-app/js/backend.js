
function getAllPostsForTeam() {
    $.ajax({
        type: "GET",
        dataType: "json",
        url: refreshLink,
        success: function(data){
            $.each(data.sins, function (i, theItem) {
                createPost(theItem.sinner, theItem.sin, 'images/stock/github.png');
            });
            refreshLink = data.refreshLink;
        }
    });
}

setInterval(
    function(){
        getAllPostsForTeam();
    }, 1000);

function createPost(sinner, sin, image) {
    var postDiv = document.createElement('div');
    postDiv.className = 'post';
    postDiv.appendChild(createImagePost(image, sinner));
    postDiv.appendChild(createSinBody(sin));
    postDiv.appendChild(createSinner(sinner));

    const newColor = "#" + Math.max(255 - postCount * 10, 0).toString(16) + "0000";
    postCount++;
    console.log(newColor);

    postDiv.style.borderColor= newColor;
    var postList = document.getElementById("post-list");
    postList.appendChild(postDiv);
    $('div.post').corner();
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