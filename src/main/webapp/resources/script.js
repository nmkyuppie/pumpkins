function deploy(){
	document.getElementById("error").classList.remove("d-block");
	document.getElementById("error").classList.add("d-none");
	document.getElementById("success").classList.remove("d-block");
	document.getElementById("success").classList.add("d-none");
	var groupId=document.getElementById("groupId").value;
	var artifactId=document.getElementById("artifactId").value;
	var version=document.getElementById("version").value;
	var e = document.getElementById("environment");
	var environment = e.options[e.selectedIndex].value;

	if(version.trim()===''){
		document.getElementById("error").classList.add("d-block");
		document.getElementById("error").classList.remove("d-none");
		document.getElementById("error").innerHTML = "Version is mandatory.";
		return;
	}
	else if(groupId.trim()===''){
		document.getElementById("error").classList.add("d-block");
		document.getElementById("error").classList.remove("d-none");
		document.getElementById("error").innerHTML = "Group Id is mandatory.";
		return;
	}
	else if(artifactId.trim()===''){
		document.getElementById("error").classList.add("d-block");
		document.getElementById("error").classList.remove("d-none");
		document.getElementById("error").innerHTML = "Artifact Id is mandatory.";
		return;
	}
	
	document.getElementById("deployBtn").innerHTML = "Deploying &nbsp;&nbsp;<span class=\"fa-1x\" style=\"display:inline;\"><i class=\"fas fa-spinner fa-spin\"></i></span>";
	document.getElementById("deployBtn").disabled = true;
	
	var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	    	document.getElementById("deployBtn").innerHTML = "Deploy";
	    	document.getElementById("deployBtn").classList.remove("disabled");
	    	document.getElementById("deployBtn").disabled = false;
	    	var message = this.responseText;
	    	
	    	if(message.search("pumpkins-success")>=0){
	    		message=message.slice(16);
				document.getElementById("success").classList.add("d-block");
				document.getElementById("success").classList.remove("d-none");
				document.getElementById("success").innerHTML = message;
	    	}
	    	else{
	    		message=message.slice(16);
				document.getElementById("error").classList.add("d-block");
				document.getElementById("error").classList.remove("d-none");
				document.getElementById("error").innerHTML = message;
	    	}
	    	
//	    	window.open(this.responseText);
	    }
	  };
	  xhttp.open("GET", "deploy?groupId="+groupId+"&artifactId="+artifactId+"&version="+version+"&environment="+environment,
			  true);
	  xhttp.send();
}

function loadVersion(version){
	window.load("?version="+version);
}

window.onresize = function(event) {
	init();
};

function init(){
	var height = window.innerHeight - 70 + 'px';
	document.getElementById('sideBar').style.height=height;
}