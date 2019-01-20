
var headers = {
    'Authorization': localStorage['token']
}
var app = new Vue({
    el: '#app',
    data: {
        selectedRoles: [],
        userId: '',
        username: '',
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        imageUrl: '',
        avatarUrl:'',
        headers:headers
    },
    mounted() {
        console.log('view mounted');
        var url = new URL(location.href);
        var userId = url.searchParams.get("userId");
        this.getUser(userId);
    },
    methods: {
        getUser(userId) {
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
                    app.imageUrl = 'http://localhost:8888/'+user.avatarUrl;
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
        handleAvatarSuccess(response, file) {
            this.imageUrl = URL.createObjectURL(file.raw);
            this.avatarUrl = response;
        },
        beforeAvatarUpload(file) {
            const isJPGorPNG = file.type === 'image/jpeg' || 'image/jpeg';
            const isLt2M = file.size / 1024 / 1024 < 2;

            if (!isJPGorPNG) {
                this.$message.error('上传头像图片只能是 JPG 或者 png 格式!');
            }
            if (!isLt2M) {
                this.$message.error('上传头像图片大小不能超过 2MB!');
            }
            return isJPGorPNG && isLt2M;
        }
    }
})