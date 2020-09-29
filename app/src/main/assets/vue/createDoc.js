let createDocument = {
    name: 'createDocument',
    template:'#create-doc-template',
components:{
    'render-doc':renderDoc
},
props:['textValue'],
created:function(){
   // createDocProps=docView.getCreateDocProperty()
 
},
computed:{
    
},
data:function(){
   return { 
    docName:'New Doc',
    isSaveEnabled:true,
    isEnabled:false,
    renderDoc:true,
    isCreateDocTemplateEnabled:true,
    documentPlaceholder:'Enter document title.',
    editDocPlaceholder: 'Edit you doc text here.'
    };
},
methods:{
    addDoc:function(){
        this.isEnabled=true;
        this.isSaveEnabled=true;
        this.isCreateDocTemplateEnabled=false;
        console.log(this.isEnabled);
        componentName='render-doc';
        console.log('invoked');
    }
}
};