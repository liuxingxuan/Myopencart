var app = new Vue({
    el: '#app',
    data: {
        selectedRoles: [],
        username: '',
        firstName: '',
        lastName: '',
        email: '',
        password: ''
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
                roles: this.selectedRoles
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
        }
    }
})