let docRow = {
    name: 'doc-row',
    template: '#show-doc-template',
    components:{
        'render-doc':renderDoc
    },
    props:['title','docValue'], 
    data:function(){
        return {
            updatedDocTitle: this.title,
            isEnabled:false,
            docText:this.docValue,
            isSaveEnabled:false
        };
    },
    methods:{
        openDoc: function(){
            console.log('open doc ');
            this.isEnabled=true;
        },
        deleteDoc: function(){
            console.log('delete doc ');
            this.isEnabled=false;
        }
    }
};
