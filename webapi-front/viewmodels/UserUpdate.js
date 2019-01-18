var app = new Vue({
    el: '#app',
    data: {
        selectedRoles: [],
        userId: '',
        username: '',
        firstName: '',
        lastName: '',
        email: '',
        password: ''
    },
    mounted() {
        console.log('view mounted');
        this.getUser();
    },
    methods: {
        getUser() {
            var url = new URL(location.href);
            var userId = url.searchParams.get("userId");
            axios.get('/user/getById', {
                params: {
                    userId: userId
                }
            })
                .then(function (response) {
                    console.log(response);
                    var user = response.data;
                    app.userId = user.userId;
                    app.username = user.username;
                    app.firstName = user.firstName;
                    app.lastName = user.lastName;
                    app.email = user.email;
                    app.selectedRoles = user.roles;
                })
                .catch(function (error) {
                    console.log(error);
                });
        },
        handleUpdateClick() {
            console.log('update click');
            this.update();
        },
        update() {
            axios.post('/user/update', {
                userId: this.userId,
                username: this.username,
                firstName: this.firstName,
                lastName: this.lastName,
                email: this.email,
                password: this.password,
                roles: this.selectedRoles,
                avatarUrl: this.avatarUrl
            })
                .then(function (response) {
                    console.log(response);
                    alert('修改成功');
                    location.href="UserIndex.html";
                })
                .catch(function (error) {
                    console.log(error);
                    alert('修改失败');
                });
        },
    }
})