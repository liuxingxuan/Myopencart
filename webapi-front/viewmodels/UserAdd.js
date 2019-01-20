var headers = {
    'Authorization': localStorage['token']
}
var app = new Vue({
    el: '#app',
    data: {
        selectedRoles: [],
        username: '',
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        imageUrl:'',
        avatarUrl:'',
        headers:headers
    },
    methods: {
        handleAddClick() {
            console.log('add click');
            this.add();
        },
        add() {
            axios.post('/user/add', {
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
                    alert('添加成功');
                    location.href = 'UserIndex.html';
                })
                .catch(function (error) {
                    console.log(error);
                    alert('添加失败');
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