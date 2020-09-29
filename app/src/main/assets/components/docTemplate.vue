<template>
<div>
    <div class="w3-col l3 s6">
      <div class="w3-container">
        <div class="w3-display-container">
          <img :src="createDocProps.editdoc.loc" style="width:100%;" /><!--../images/doc.png-->
          <button @click="editDoc()" >{{documentTitle}}</button>
          <input type="hidden" v-model="docId" :value="documentId"/>
         <!-- <div class="w3-display-middle w3-display-hover">
            <button  class="w3-button w3-black" onclick="editDoc()">Edit Document<i class="fa "></i></button>
          </div>-->
        </div>
        
      </div>
     <!-- <div class="w3-container">
        <img src="../images/trash.png" alt="Delete" style="width:30%" onclick="delete(docId)"/>
      </div>-->
    </div>
    <renderDoc v-if="enabled" :docName="docId" :isSaveEnabled="isSaveEnabled"></renderDoc>
</div>
</template>

<script>
import renderDoc from "./renderDoc";
export default {
    name: 'docTemplate',
    components: {
        renderDoc
    },
    created:{

    },
    props:['documentId','documentTitle'],
    data:{
        docId:'',
        enabled:false,
        isSaveEnabled:true
    },
    methods:{
      editDoc: function(){
          enabled=true;
          if(docId!=='-1'){
            isSaveEnabled=false;
          }
      }  
    }
}
</script>