/**
 * ${tableDesc}模块页面脚本
 * @author: ${author}
 * @date：${currentDate}
 * @version：${projectVersion}
 */
$(function() {
	loadProperties();
	if(!globalSetting()) {
		window.location.href = WEB_BASE + '/login.html';
		return;
	}
	bindBtn();
});

/**
 * 绑定各个功能按钮
 * 
 * @returns
 */
function bindBtn() {
	//TODO:绑定各个功能按钮
}
