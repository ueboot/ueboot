export default {
  get(_this){
    return [{
      key:'a',
      render:(h,params)=>{
        _this.$emit("input",'')
        //这里的_this代表的是外部调用传入进来的
      }
    }]
  }
}
