<template>
<div>
    <renderDoc v-if="newDoc" :docName="docName" :isSaveEnabled="isSaveEnabled">"</renderDoc>
    <div class="w3-col l3 s6">
      <div class="w3-container">
        <div class="w3-display-container">
          <img src="../images/doc.png" style="width:100%;" /><!--../images/doc.png-->
          
          <div class="w3-display-middle w3-display-hover">
            <button class="w3-button w3-black" onclick="addDoc()">Add Document<i class="fa "></i></button>
          </div>
        </div>
        
      </div>
    </div>
</div>
</template>


<script>

import renderDoc from "./renderDoc";
export default {
    name: 'createDocument',
    components:{
        renderDoc
    },
    created:{
       // createDocProps=docView.getCreateDocProperty()
    },
    data:{
        createDocProps:{},
        docName:'New Doc',
        isSaveEnabled:true,
        newDoc:false
    },
    methods:{
        addDoc:function(){
            newDoc=true;
        }
    }
}
</script>