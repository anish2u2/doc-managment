<template>
    <div class="w3-col l3 s6">
      <div class="w3-container">
        <input type="text" v-model="docTitle" :value="docTitle" :placeholder="documentPlaceholder"/>
        <input type="hidden" :value="docName" v-model="defaultDocName"/>
      </div>
      <div class="w3-container">
        <div class="w3-display-container">
          <img :src="createDocProps.editdoc.loc" style="width:100%;" /><!--../images/doc.png-->
          <textarea id="docEditArea" :placeholder="editDocPlaceholder" v-model="docText"  rows="4" cols="50"></textarea>
          <!--<div class="w3-display-middle w3-display-hover">
            <button class="w3-button w3-black" onclick="editDoc()">Edit Document<i class="fa "></i></button>
          </div>-->
        </div>
        <div class="w3-container">
            <input type="image" v-if="isSaveEnabled" v-model="docId" src="../images/save.jpg" alt="Save" style="width:50%;" @click="saveDoc"/>
            <input type="image" v-if="!isSaveEnabled" v-model="docId" src="../images/update.jpg" alt="Update" style="width:50%;" @click="updateDoc"/>
            <input type="image" v-model="docId" src="../images/cancel.png" alt="Delete" style="width:50%;" @click="deleteDoc"/>
         </div>
       </div>
      
    </div>
</template>
<style>
    button-enabled {
        display: block;
    }
    button-disabled {
        display: none;
    }
</style>
<script>
export default {
    name: 'renderDoc',
    created:{
        
    },
    props:['docName','isSaveEnabled'],
    data:{
        docTitle:'',
        docId:'',
        docText: '',
        saveDocClass: '',
        updateDocClass:'',
        defaultDocName:'',
        documentPlaceholder:'Enter document title.',
        editDocPlaceholder: 'Edit you doc text here.'
    },
    methods:{
        saveDoc:function(){

        },
        updateDoc:function(){

        },
        deleteDoc:function(){

        }
    }
}
</script>