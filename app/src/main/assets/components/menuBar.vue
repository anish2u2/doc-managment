<template>

<nav class="w3-sidebar w3-bar-block w3-white w3-collapse w3-top" style="z-index:3;width:250px" id="mySidebar">
  <div class="w3-container w3-display-container w3-padding-16">
    <i onclick="w3_close()" class="fa fa-remove w3-hide-large w3-button w3-display-topright"></i>
    <h3 class="w3-wide"><b><img src="../images/doc_logo.jpg" alt="Documents" style="width:15%">Manager</b></h3>
  </div>
  <div class="w3-padding-64 w3-large w3-text-grey" style="font-weight:bold">
    <a href="#" class="w3-bar-item w3-button">Read</a>
    <a href="#" class="w3-bar-item w3-button">Create</a>
	<a href="#" class="w3-bar-item w3-button">Update</a>
	<a href="#" class="w3-bar-item w3-button">Delete</a>
  </div>
  
</nav>

</template>

<script>
export default {
    name: 'menuBar',
    created:{
        //navProp=docView.getMessageBundle();
    },
    data:{
       navProp:{} 
    }
}
</script>