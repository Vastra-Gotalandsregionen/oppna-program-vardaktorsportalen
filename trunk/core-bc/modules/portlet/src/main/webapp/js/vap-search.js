AUI().add('vap-search',function(A) {
    var Lang = A.Lang,
        isArray = Lang.isArray,
        isFunction = Lang.isFunction,
        isNull = Lang.isNull,
        isObject = Lang.isObject,
        isString = Lang.isString,
        isUndefined = Lang.isUndefined,
        getClassName = A.ClassNameManager.getClassName,
        concat = function() {
            return Array.prototype.slice.call(arguments).join(SPACE);
        },
        
        AC_CONTENT_BOX = 'autoCompleteContentBox',
        AC_INPUT = 'autoCompleteInput',
        
        NAME = 'vap-search',
        NS = 'vap-search',
        
        SEARCH_TERM_PARAM_NAME = 'searchTermParamName',
        
        PORTLET_NAMESPACE = 'portletNamespace'
        
        URL_AUTO_COMPLETE = 'urlAutoComplete',
        
        CSS_HIDDEN = 'aui-helper-hidden'
    ;
        
    var VapSearch = A.Component.create(
            {
                ATTRS: {
                	autoCompleteContentBox: {
                		value: '.vap-autocomplete-wrap',
                		setter: A.one
                	},
                	autoCompleteInput: {
                		value: '.vap-autocomplete-input',
                		setter: A.one
                	},
                	portletNamespace: {
                		portletNamespace: ''
                	},
                	searchTermParamName: {
                		value: 'autoCompleteSearchTerm'
                	},
                	urlAutoComplete: {
                		value: ''
                	}
                },
                EXTENDS: A.Component,
                NAME: NAME,
                NS: NS,
                
                prototype: {
                	
                	autoComplete: null,
                    
                    initializer: function(config) {
                        var instance = this;
                    },
                    
                    renderUI: function() {
                        var instance = this;
                        
                        // Init console for debugging
                        //instance._initConsole();
                        
                        instance._initAutoComplete();
                    },
    
                    bindUI: function() {
                        var instance = this;
                    },
                    
                    _initAutoComplete: function() {
                    	var instance = this;
                    	
                    	var url = instance.get(URL_AUTO_COMPLETE) + '&' + instance.get(SEARCH_TERM_PARAM_NAME) + '=';
                    	
                    	var myDataSource = new A.DataSource.IO({source: url});
                    	
                        myDataSource.plug(A.Plugin.DataSourceJSONSchema, {
                            schema: {
                                resultListLocator: 'result',
                                resultFields: ['suggestion']
                            }
                        });
                        
                        var acInput = instance.get(AC_INPUT);
                        
                        instance.autoComplete = new A.AutoCompleteList({
	                        cssClass: 'vap-autocomplete',
	                        source: myDataSource,
	                        inputNode: acInput,
	                        minQueryLength: 2,
                            queryDelay: 0.2,
                            resultFilters: [function (query, result) {
                                var items = JSON.parse(result[0].raw.response)['result'];
                                var array = [];

                                items.forEach(function(entry) {
                                    array.push({'display':entry['suggestion'], 'text':entry['suggestion']});
                                });

                                return array;
                            }]
                        });

                        instance.autoComplete.render();            
                    },
                    
                    _initConsole: function() {
                    	var instance = this;
                    	
                    	var consoleSettings = {
                    	        newestOnTop: true,
                    	        visible: true
                        	};
                        	
                        	var console =  new A.Console(consoleSettings).render();
                    },
                    
                    _someFunction: function() {
                        var instance = this;
                    }

                }
            }
    );

    A.VapSearch = VapSearch;
        
    },1, {
        requires: [
	       'autocomplete',
           'autocomplete-sources',
           'aui-base',
	       'datasource-io',
	       'json',
	       'console'
      ]
    }
);
