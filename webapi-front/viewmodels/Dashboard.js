var headers = {
    'Authorization': localStorage['token']
}
var app = new Vue({
    el: '#app',
    data: {
        index:1
    },
    methods:{
        handleSelectClick(val){
            console.log(val);
            this.index = val;
            this.href();
        },
        href(){
            location.href = "UserIndex.html";
        }
    }
})