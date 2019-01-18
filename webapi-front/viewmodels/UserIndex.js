var app = new Vue({
    el: '#app',
    data: {
       pageInfo:[],
       pageNum:1,
       selectedUserIds:[]
    },
    mounted(){
        this.getUsers();
    },
    methods:{
        getUsers(){
            axios.get('/user/getUsersWithPage',{
                params:{
                    pageNum:this.pageNum
                }
            })
                .then(function(response){
                    console.log(response);
                    app.pageInfo = response.data;
                })
                .catch(function (error) {
                    console.log(error);
                });
        },
        handleSelectionChange(users){
            console.log(users);
            this.selectedUserIds = users.map(user=>user.userId);
        },
        batchDeleteClick(){
            console.log('batch delete click');
            this.batchDelete();
        },
        batchDelete(){
            axios.post('/user/batchdelete', this.selectedUserIds)
              .then(function (response) {
                console.log(response);
                alert('删除成功');
                location.reload();
              })
              .catch(function (error) {
                console.log(error);
                alert(error.response.data.message);
              });
        },
        addClick(){
            console.log("add click");
            location.href="UserAdd";
        },
        handleEdit(userId){
            console.log(userId);
            // sessionStorage["user"] = JSON.stringify(user);
            location.href="UserUpdate?userId="+userId;
        }
    }
  
      
})