app.controller('brandController',function ($scope,$controller,brandservice) {
    $controller('baseController',{$scope:$scope});
    $scope.searchEntity={};
    $scope.search=function (page,rows) {
        brandservice.search(page,rows,$scope.searchEntity).success(
            function (response) {
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;
            }
        );
    }

    $scope.save=function () {
        var serviceObject;
        if($scope.entity.id){
            serviceObject=brandservice.update($scope.entity);
        }else{
            serviceObject=brandservice.add($scope.entity);
        }

        serviceObject.success(
            function (response) {
                if(response.success){
                    $scope.reloadList();
                }else {
                    alert(response.message)

                }
            }
        )
    }

    $scope.findOne=function (id) {
        brandservice.findOne(id).success(
            function (response) {
                $scope.entity=response;
            })
    }

    $scope.dele=function () {
        brandservice.dele($scope.selectIds).success(
            function (response) {
                if(response.success){
                    $scope.reloadList();
                }else{
                    alert(response.message);
                }

            }
        )
    }




})