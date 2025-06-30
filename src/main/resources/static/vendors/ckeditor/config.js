/**
 * @license Copyright (c) 2003-2019, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see https://ckeditor.com/legal/ckeditor-oss-license
 */

CKEDITOR.editorConfig = function( config ) {
    // console.log(config);
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
    config.removeButtons = 'Save';
    config.extraPlugins = 'mathjax, pastebase64';
    config.mathJaxLib = '//cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/MathJax.js?config=TeX-AMS_HTML';
    config.contentsCss = baseUrl+'mdbootstrap/css/fonts.css';
    config.font_names = 'k|Llt g]kfnL/Preeti;'+config.font_names;
};
