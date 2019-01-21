
var headers = {
    'Authorization': localStorage['token']
}
var app = new Vue({
    el: '#app',
    data: {
        productId: '',
        name: '',
        productCode: '',
        price: '',
        brand: '',
        imageUrl: '',
        picMainUrl:'',
        headers:headers
    },
    mounted() {
        console.log('view mounted');
        var url = new URL(location.href);
        var productId = url.searchParams.get("productId");
        this.getProduct(productId);
    },
    methods: {
        getProduct(productId) {
            axios.get('/product/getById', {
                params: {
                    productId: productId
                }
            })
                .then(function (response) {
                    console.log(response);
                    var product = response.data;
                    app.productId = product.productId;
                    app.name = product.name;
                    app.productCode = product.productCode;
                    app.price = product.price;
                    app.brand = product.brand;
                    app.imageUrl = 'http://localhost:8888/'+product.pictureMainUrl;
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
            axios.post('/product/update', {
                productId: this.productId,
                name: this.name,
                productCode: this.productCode,
                price: this.price,
                brand: this.brand,
                picMainUrl: this.picMainUrl,
            })
                .then(function (response) {
                    console.log(response.data);
                    alert('修改成功');
                    location.href="ProductIndex.html";
                })
                .catch(function (error) {
                    console.log(error);
                    alert('修改失败');
                });
        },
        handleAvatarSuccess(response, file) {
            this.imageUrl = URL.createObjectURL(file.raw);
            this.picMainUrl = response;
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