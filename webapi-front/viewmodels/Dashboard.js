var app = new Vue({
    el: '#app',
    data: {
        index:1
    },
    methods:{
        handleSelectClick(val){
            console.log(val);
            this.index = val;
        },
    }
})