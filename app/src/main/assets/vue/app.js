
var renderDoc={

    template:`<div>

    <div class="form-group">
        <div class="has-error" v-show="hasError">{{errorMessage}}</div>
        <input type="text" class="form-control" value="docTitle" v-model="docTitle" :placeholder="documentPlaceholder"/>
        <input type="hidden"  v-model="defaultDocName"/>
    </div><br/>


    <textarea id="docEditArea" class="form-control" rows="10" v-model="docText" :placeholder="editDocPlaceholder"></textarea>
    <br/>

    <table>
        <tr>
            <td v-if="isSaveEnabled" ><input type="image" v-if="isSaveEnabled" v-model="docId" src="../images/save.jpg" alt="Save" style="width:50px;height:50px;" @click="saveDoc()"/></td>
            <td v-if="!isSaveEnabled" ><input type="image" v-if="!isSaveEnabled" v-model="docId" src="../images/update.jpg" alt="Update" style="width:50px;height:50px;" @click="updateDoc()"/></td>
            <td ><input type="image" v-model="docId" src="../images/cancel.png" alt="Delete" style="width:50px;height:50px;" @click="cancel()"/></td>
        </tr>
    </table>


</div>`,

props:['docName','commentValue','isSaveEnabled','styleClass','showDoc'],
data: function(){
return {
    previousTextValue: this.docName,
    docTitle:this.docName,
    docId:'',
    docText: this.commentValue,
    defaultDocName:'',
    isRenderDocument: this.showDoc,
    documentPlaceholder:'Enter document title.',
    editDocPlaceholder: 'Edit you doc text here.',
    hasError:false,
    errorMessage:''
};
},
methods:{
    saveDoc:function(){
        //console.log('Doc text:'+this.docText);
        var response=docView.save(this.docTitle,this.docText);
        if(response===true||response==='true'){
            this.hasError=false;
            this.isRenderDocument=false;
            this.$emit('messageFromChild');
            var doc=new Object();
            doc.docTitle=this.docTitle;doc.docText=this.docText;
            this.$emit('save-request',doc);
        }else{
            this.hasError=true;
            this.errorMessage='Unable to Save Document.';
        }

    },
    updateDoc:function(){
        var response=docView.update(this.docName,this.previousTextValue,this.docText);
        if(response===true||response==='true'){
            this.hasError=false;
            this.isRenderDocument=false;
            this.$emit('messageFromChild');
            var doc=new Object();
             doc.docTitle=this.docTitle;doc.docText=this.docText;
            this.$emit('update-request',doc,this.previousTextValue);
        }else{
            this.hasError=true;
            this.errorMessage='unable to update document.';
        }

    },
    deleteDoc:function(){
        var response=docView.delete(this.docName);
        if(response===true||response==='true'){
            this.hasError=false;
            this.isRenderDocument=false;
            this.$emit('messageFromChild');
        }else{
            this.hasError=true;
            this.errorMessage='Error message goes here.';
        }

    },cancel: function(){

        this.isRenderDocument=false;
        this.$emit('messageFromChild');
    }

},
computed:{

}
};

var docRow= {
    components:{
        'render-doc':renderDoc
    },
    template: `<div v-show="present">
    <div  style="padding-left:10%">
        <div class="table">
            <div class="row">
                <div class="td">

                    <button type="button" :name="updatedDocTitle" class="btn btn-secondary"  @click="openDoc()" :value="updatedDocTitle">{{updatedDocTitle}}</button>
                </div>
                <div class="td">
                    <img src="../images/trash.png" style="width:35%;float:right;cursor:pointer;"  @click="deleteDoc()"/>
                </div>
            </div>
        </div>
        <div class="has-error" v-show="hasError">{{errorMessage}}</div>
    </div>
    <br>
    <div v-show="isEnabled">
        <render-doc v-on:update-request="updateDoc" v-on:messageFromChild="listenMessageFromChild" v-bind:docName="updatedDocTitle" v-bind:showDoc="present" v-bind:isSaveEnabled="isSaveEnabled" v-bind:commentValue="docValue"></render-doc>
    </div>
    <br/>
</div>`,

    props:['title','docValue','document'],
    data:function(){
        return {
            updatedDocTitle: this.title,
            isEnabled:false,
            present:true,
            docText:this.docValue,
            isSaveEnabled:false,
            errorMessage:'',
            hasError:false
            //buttonTitle:this.title
        };
    },
    computed:{
        buttonTitle:function(){
            return this.title;
        }
    },

    methods:{
        openDoc: function(){
            this.isEnabled=true;
        },
        deleteDoc: function(){
            var response=docView.delete(this.updatedDocTitle);
            if(response===true||response==='true'){
                this.hasError=false;
                this.isEnabled=false;
                this.present=false;
                this.$emit('deletRequestFromChild',this.updatedDocTitle);
            }else{
                this.hasError=true;
                this.errorMessage='Unable to delete document.';
            }


        },
        updateDoc : function(doc,oldDocTitle){
            this.$emit('update-doc-row',doc,oldDocTitle);
        },
        listenMessageFromChild: function(){
            this.isEnabled=false;
        }
    }
};




    Vue.component('createDocument' ,{
        components:{
            'render-doc':renderDoc
        },
        template:`<div>
        <div  v-show="isEnabled">
            <render-doc v-on:save-request="triggerSave"  :isSaveEnabled="isSaveEnabled" ></render-doc>
        </div>
        <div class="w3-col l3 s6" v-show="isCreateDocTemplateEnabled">
            <div class="w3-container">
                <div class="w3-display-container">
                    <img src="../images/doc.png" style="width:100%;" /><!--../images/doc.png-->

                    <div class="w3-display-middle w3-display-hover">
                        <button class="w3-button w3-black" @click="addDoc()">Add Document<i class="fa "></i></button>
                    </div>
                </div>

            </div>
        </div>
    </div>`,

    props:['textValue'],

    computed:{

    },
    data:function(){
       return {
        docName:'New Doc',
        isSaveEnabled:true,
        isEnabled:false,
        isCreateDocTemplateEnabled:true,
        documentPlaceholder:'Enter document title.',
        editDocPlaceholder: 'Edit you doc text here.'
        };
    },
    methods:{
        triggerSave: function(doc){
            this.isEnabled=false;
            this.isCreateDocTemplateEnabled=true;
            this.$emit('update-doc-row',doc);
        },
        addDoc:function(){
            this.isEnabled=true;
            this.isSaveEnabled=true;
            this.isCreateDocTemplateEnabled=false;

        },
        listenMessageFromChild: function(){
            this.isEnabled=false;
            this.isCreateDocTemplateEnabled=true;
        }
    }
    });

    Vue.component('login-template' ,{
        template:` <div>
        <div class="table" v-if="isHomeRequest">
            <div class="row">
                <div class="td" style="padding:10%;"><button  class="btn btn-secondary" @click="login()">{{loginTitle}} </button></div>
                <div class="td" style="padding:10%;"><button  class="btn btn-secondary" @click="register()">{{SignUpTitle}} </button></div>
            </div>
        </div>
        <div class="form-group" v-else-if="showLogin">
            <input type="password" class="form-control"  v-model="password" :placeholder="passwordPlaceHolder"/>

            <div class="table">
                <div class="row">
                    <div class="td" style="padding:10%;"><button  class="btn btn-secondary" v-on:keyup="clearError()" @click="loginRequest()">{{loginTitle}} </button></div>
                    <div class="td" style="padding:10%;"><button  class="btn btn-secondary" v-on:keyup="clearError()" @click="cancelRequest()">{{cancel}} </button></div>
                </div>
            </div>
        </div>
        <div class="form-group" v-else-if="showRegister">
            <input type="text" class="form-control" v-on:keyup="clearError()"  v-model="password" :placeholder="passwordPlaceHolder"/>
            <input type="text" class="form-control" v-on:keyup="clearError()" v-model="confirmPassword" :placeholder="confirmPasswordPlaceHolder"/>
            <div class="table">
                <div class="row">
                    <div class="td" style="padding:10%;"><button  class="btn btn-secondary" @click="signUp()">{{SignUpTitle}} </button></div>
                    <div class="td" style="padding:10%;"><button  class="btn btn-secondary" v-on:keyup="clearError()" @click="cancelRequest()">{{cancel}} </button></div>
                </div>
            </div>
        </div>
        <div class="has-error" v-show="hasError">{{errorMessage}}</div></div>`,

    props:[],

    computed:{

    },
    data:function(){
       return {
        loginTitle: 'Login',
        SignUpTitle: 'SignUp',
        passwordPlaceHolder: 'Enter your password',
        confirmPasswordPlaceHolder: 'Confirm your password',
        cancel:'Cancel',
        isHomeRequest:true,
        showLogin:false,
        showRegister:false,
        password:'',
        confirmPassword:'',
        errorMessage:'',
        hasError:false
        };
    },
    methods:{
        register: function(){
            this.hasError=false;
            this.showLogin=false;
            this.isHomeRequest=false;this.showRegister=true;
        },loginRequest:function(){
            var response=docView.login(this.password);
                            if(response==='SUCCESS'){
                                 this.hasError=false;
                                this.$emit('show-options');
                            }else if(response==='FAILURE'){
                                this.errorMessage='incorrect password!';
                                                this.hasError=true;
                            }


        },signUp:function(){
            if(this.password===this.confirmPassword){
                var response=docView.signUp(this.password,this.confirmPassword);
                if(response==='SUCCESS'){
                    this.hasError=false;
                    this.$emit('show-options');
                }else if(response==='FAILURE'){
                    this.errorMessage='Unable to SignUp.';
                    this.hasError=true;
                }
                //this.$emit('show-options');
            }else{
                this.errorMessage='Password do not match!';
                this.hasError=true;
            }
        },
        login:function(){
            this.showLogin=true;
            this.isHomeRequest=false;this.showRegister=false;
        },
        clearError: function(){
            this.hasError=false;
        },
        cancelRequest: function(){
            this.hasError=false;
            this.showLogin=false;
             this.isHomeRequest=true;
             this.showRegister=false;
        }
    }
    });

    Vue.component('button-component',{
        props:{
            doc:{
                type:Object
            }
        }, data: function () {
            return {
              count: 0
            }
          },
        template: '<button v-on:click="count++">You clicked me {{doc.docTitle}} {{ count }} times.</button>'
    });

    new Vue({
        el: '#docApp',
        components:{
            'doc-row':docRow
        },
        props:['errorMessage','SignUpTitle','loginTitle','cancel','docText'],
        created: function (){
          /*  var response=docView.fetchAllDoc();
            console.log('response from services:'+response);
            if(response!=='EMPTY')
             {
                this.docs=JSON.parse(response);
              }*/
            this.isDataInitialized=true;
        },
        data:{
            isDataInitialized:false,
            docs:[] ,
            showLoginPage:true
        },
        methods:{
            updateDocsRows : function(doc){
                this.docs.push(JSON.parse(JSON.stringify(doc)));
            },
            hideLogin: function(){
                var response=docView.fetchAllDoc();
                // console.log('response from services:'+response);
                 if(response!=='EMPTY')
                 {
                 this.docs=JSON.parse(response);
                 /*for(var counter=0;counter<data.length;counter++){
                    var documents=new Object();
                    document.docTitle=data[counter].docTitle;
                    document.docText=data[counter].docText;
                    this.docs.push(document);
                 }*/

                 }
                this.showLoginPage=false;

            },
            removeDoc: function(docName){
                var data=[];
                if(this.docs!=null){
                    for(var counter=0;counter<this.docs.length;counter++){
                        if(!docName===this.docs[counter] ){
                            data.push(this.docs[counter]);
                        }

                    }
                    this.docs=data;
                }
            },
            updateDocRow : function(doc,oldDocTitle){
            doc=JSON.parse(JSON.stringify(doc));
                for(var counter=0;counter<this.docs.length;counter++){
                    if(oldDocTitle === this.docs[counter].docTitle){
                        this.docs[counter].docTitle=doc.docTitle;
                        this.docs[counter].docText=doc.docText;
                        break;
                    }
                }
            }
        },
       computed: {
           documentsList: function(){
               return this.docs;
           }
       }
    })

    function myAccFunc() {
        var x = document.getElementById("demoAcc");
        if (x.className.indexOf("w3-show") == -1) {
          x.className += " w3-show";
        } else {
          x.className = x.className.replace(" w3-show", "");
        }
      }
      
    
      
      
      function w3_open() {
        document.getElementById("mySidebar").style.display = "block";
        document.getElementById("myOverlay").style.display = "block";
      }
       
      function w3_close() {
        document.getElementById("mySidebar").style.display = "none";
        document.getElementById("myOverlay").style.display = "none";
      }