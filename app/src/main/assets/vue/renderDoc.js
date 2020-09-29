let renderDoc = {
    name: 'render-doc',
    template:'#render-doc-template',
  created:function(){
    
},
props:['docName','commentValue','isSaveEnabled','styleClass','enabled'],
data: function(){
return {        
    docTitle:this.docName,
    docId:'',
    docText: this.commentValue,
    defaultDocName:'',
    documentPlaceholder:'Enter document title.',
    isEnabled:this.enabled,
    editDocPlaceholder: this.textValue//'Edit you doc text here.'
};
},
methods:{
    saveDoc:function(){
        console.log(this.docTitle);
        console.log(this.docText);
        this.isEnabled=false;
    },
    updateDoc:function(){
        this.isEnabled=false;
    },
    deleteDoc:function(){
        this.isEnabled=false;
    },cancel: function(){
        //console.log('reload');
        //window.location.reload();
        this.isEnabled=false;
    }
    
},
computed:{
    
}
};