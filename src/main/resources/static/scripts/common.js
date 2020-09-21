/**
* 비동기 호출 공통
*
* */
const devCommon = {};
devCommon.xhr = function(option){

	let method = option.method;
	let url = option.url;
	let callbackFunc = option.callbackFunc;
	let params = option.params;
	let async = (option.async === undefined || option.async === null) ? true : option.async;

	let xhr;
	try {
		if (window.ActiveXObject) {
			xhr = new ActiveXObject("Microsoft.XMLHTTP");
		} else {
			xhr = new XMLHttpRequest();
		}

	} catch (e) {
		alert("Error creating the XMLHttpRequest object.");
		return;
	}

	xhr.onreadystatechange = function () {
		if (this.readyState === 4 && this.status === 200) {
			//console.log('요청 완료및 응답 준비, 데이터를 전부 받음(' + this.status + ')');
			console.log('printError');
			if (typeof callbackFunc === 'function') {
				callbackFunc(this);
			} else {
				console.info('No callback function');
			}
		} else {
			console.log("error==" + this.readyState);
		}
	};

	// 네트워크 수준의 에러시 처리 내용
	xhr.onerror = function () {
		console.error('Maybe network error');
		console.error('printError', this.responseText);
	};

	xhr.open(method, url, async);

	// get 이외 csrf 적용
	if (method.toUpperCase() !== "GET") {
		const header = document.querySelector('meta[name="_csrf_header"]').getAttribute("content");
		const token = document.querySelector('meta[name="_csrf"]').getAttribute("content");
		xhr.setRequestHeader(header, token);
		if (option.contentType) {
			xhr.setRequestHeader('Content-type', option.contentType);
		} else {
			xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
		}

	} else {
		params = null;
	}
	xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
	xhr.send(params);
};
devCommon.logOut = function() {
	const option = {
		method: 'POST',
		url: '/logout',
		params: {'_csrf': document.querySelector('meta[name="_csrf"]').getAttribute("content")},
		callbackFunc: function(){
			location.reload();
		}
	};
	devCommon.xhr(option);
};
