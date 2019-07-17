(function() {
	var issues = [];
	if (window.localStorage) {
		localStorage.setItem('issues', JSON.stringify(issues));
	} else {
		// can't be used
	}

	document.getElementById('issueInputForm').addEventListener('submit',
			saveIssue);
	
})();

function fetchIssues() {
	makeAjaxCall("http://localhost:8080/book/getAll", "GET").then(processBookGetAllResponse, errorHandler);	
}

function saveIssue(e) {	
	var bookDesc = document.getElementById('bookDescInput').value;
	var bookIsbn = document.getElementById('bookIsbn').value;
	var bookTitle = document.getElementById('bookTitle').value;
	
	var book = {
		"description": bookDesc,
		"isbn": bookIsbn,
		"title": bookTitle,
	}

	makeAjaxCall("http://localhost:8080/book/add", "POST",JSON.stringify(book)).then(processBookSaveResponse, errorHandler);

	document.getElementById('issueInputForm').reset();

	fetchIssues();

	e.preventDefault();
}
function processBookSaveResponse(books){
	console.log(book);
}
function setStatusClosed(id) {
	var issues = JSON.parse(localStorage.getItem('issues'));

	for (var i = 0; i < issues.length; i++) {
		if (issues[i].id == id) {
			issues[i].status = "Closed";
		}
	}

	localStorage.setItem('issues', JSON.stringify(issues));

	fetchIssues();
}

function deleteIssue(id) {
	var issues = JSON.parse(localStorage.getItem('issues'));

	for (var i = 0; i < issues.length; i++) {
		if (issues[i].id == id) {
			issues.splice(i, 1);
		}
	}

	localStorage.setItem('issues', JSON.stringify(issues));

	fetchIssues();
}

function makeAjaxCall(url, methodType,data){
	   var promiseObj = new Promise(function(resolve, reject){
		  var xhr = new XMLHttpRequest();		  
	      xhr.open(methodType, url, true);
	      if(data)
	    	  xhr.setRequestHeader("Content-Type","application/json");	      
	      xhr.send(data);
	      xhr.onreadystatechange = function(){
	      if (xhr.readyState === 4){
	         if (xhr.status === 200){
	            console.log("xhr done successfully");
	            var resp = xhr.responseText;
	            var respJson = JSON.parse(resp);
	            resolve(respJson);
	         } else {
	            reject(xhr.status);
	            console.log("xhr failed");
	         }
	      } else {
	         console.log("xhr processing going on");
	      }
	   }
	   console.log("request sent succesfully");
	 });
	 return promiseObj;
	}
	
	function processBookGetAllResponse(books){
		console.log(books);
		var issues = books;
		var issueList = document.getElementById('issuesList');
		issueList.innerHTML = '';
		for (var i = 0; i < issues.length; i++) {
			var id = issues[i].identifier;
			var desc = issues[i].description;
			var title = issues[i].title;
			var isbn = issues[i].isbn;
			//var status = issues[i].status;
			issuesList.innerHTML += '<div class="well">'
					+ '<h6>Issue ID: '
					+ id
					+ '</h6>'					
					+ '<h3>'
					+ desc
					+ '</h3>'
					+ '<p><span >Title:</span> '
					+ title
					+ ' '
					+ '<span >ISBN:</span> '
					+ isbn
					+ '</p>'
					+ '<a href="#" class="btn btn-warning" onclick="setStatusClosed(\''
					+ id + '\')">Close</a> '
					+ '<a href="#" class="btn btn-danger" onclick="deleteIssue(\''
					+ id + '\')">Delete</a>' + '</div>';
		}
	}
	
	function errorHandler(statusCode){
	 console.log("failed with status", status);
	}