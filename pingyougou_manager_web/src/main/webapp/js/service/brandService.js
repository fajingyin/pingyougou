app.service('brandservice',function ($http) {
    this.search=function(page,rows,searchEntity){
       return  $http.post('../brand/search.do?page='+page+'&rows='+rows,searchEntity);
    }

    this.update=function (entity) {
        return $http.post('../brand/update.do',entity);
    }

    this.add=function (entity) {
        return $http.post('../brand/add.do',entity);
    }

    this.findOne=function (id) {
        return $http.get("../brand/findOne.do?id="+id);
    }
    this.dele=function (ids){
        return $http.get('../brand/delete.do?ids='+ids);
    }
})