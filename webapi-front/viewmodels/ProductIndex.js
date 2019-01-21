var app = new Vue({
    el: '#app',
    data: {
       pageInfo:[],
       pageNum:1,
       selectedProducts:[]
    },
    computed:{
        products(){
            return this.pageInfo.list;
        }
    },
    mounted(){
        this.getProducts();
    },
    methods:{
        handleCurrentChange(val){
            this.pageNum = val;
            this.getProducts();
        },
        getProducts(){
            axios.get('/product/getWithPage',{
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
        handleSelectionChange(products){
            console.log(products);
            this.selectedProducts = products.map(product=>product.productId);
        },
        batchDeleteClick(){
            console.log('batch delete click');
            this.batchDelete();
        },
        batchDelete(){
            axios.post('/product/batchdelete', this.selectedProducts)
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
            location.href="ProductAdd";
        },
        handleEdit(productId){
            console.log(productId);
            // sessionStorage["user"] = JSON.stringify(user);
            location.href="ProductUpdate?productId="+productId;
        }
    }
  
      
})