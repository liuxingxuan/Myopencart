var app = new Vue({
    el: '#app',
    data: {
        username:'',
        password:''
    },
  
    methods: {
        loginClick(){
            console.log('loginclick');
            this.login();
        },
        login(){
            axios.get('http://localhost:8080/user/login',{
                params:{
                    username:this.username,
                    password:this.password
                }
            })
            .then(function (response) {
                console.log(response);
                localStorage['token'] = 'Myopencart '+response.data;
                location.href='Dashboard.html';
            })
            .catch(function (error) {
                console.log(error.response.data);
            });
        }
	}
      
})