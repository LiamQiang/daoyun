import axios from 'axios'
import Vue from 'vue'
import router from '../router'

// 设置请求发送cookie, 默认是false, 不发送
axios.defaults.withCredentials = true;

// // 添加请求拦截器
axios.interceptors.request.use(config => {
    let loading = Loading.service({
        fullscreen: true,
        text: '拼命加载中...'
    });
    return config
}, err => {
    // 对请求错误的处理
    console.log("发送失败");
    let loading = Loading.service({});
    loading.close();    //关闭加载前，记得重新定义实例
    return Promise.reject(err)
});

// 添加响应拦截器,处理异常错误
axios.interceptors.response.use(res => {
  let loading = Loading.service({});
  loading.close();
  //未登录情况
  if (res.message === '尚未登录') {
     store.commit('logout');
     router.push({
         path: '/home',
         query: {redirect: router.currentRoute.fullPath}
     });
     return Promise.reject({message: "请先登录"});
  }
  //错误,非正常返回
  if (res.data.status != 1) {
     return Promise.reject({message: res.data.info});
  }
  return res;
}, err => {
  if (err && err.response) {
    switch (err.response.status) {
	  case 500:
        err.message = '服务器内部错误!';
        break;
      case 404:
        err.message = '未找到指定文件!';
        break;
      case 403:
        err.message = '权限不足';
        break;
      default:
        err.message = '获取失败!';
    }
  }
  return Promise.reject(err);
});
axios.install = (Vue) => {
  Vue.prototype.$axios = axios;
};

export default axios;
