AUI().add('vap-search-results',function(A) {
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
        
        NAME = 'vap-search-results',
        NS = 'vap-search-results',
        
        RESULTS_WRAP = 'resultsWrap',
        
        CSS_HIDDEN = 'aui-helper-hidden',
        CSS_SEARCH_RESULTS_ITEM = 'vap-search-result-item',
        CSS_SEARCH_RESULTS_ITEM_HOVER = 'vap-search-result-item-hover'
    ;
        
    var VapSearchResults = A.Component.create(
            {
                ATTRS: {
                	resultsWrap: {
                		value: '.vap-results-wrap',
                		setter: A.one
                	}
                },
                EXTENDS: A.Component,
                NAME: NAME,
                NS: NS,
                
                prototype: {
                    
                    initializer: function(config) {
                        var instance = this;
                    },
                    
                    renderUI: function() {
                        var instance = this;
                        
                        // Init console for debugging
                        //instance._initConsole();
                    },
    
                    bindUI: function() {
                        var instance = this;
                        
                        var resultsWrap = instance.get(RESULTS_WRAP) 
                        
                        if(!isNull(resultsWrap)) {
                        	resultsWrap.delegate('mouseenter', instance._onMouseEnterResultsItem, '.' + CSS_SEARCH_RESULTS_ITEM, instance);
                        	resultsWrap.delegate('mouseleave', instance._onMouseLeaveResultsItem, '.' + CSS_SEARCH_RESULTS_ITEM, instance);
                        }
                    },
                    
                    _initConsole: function() {
                    	var instance = this;
                    	
                    	var consoleSettings = {
                    	        newestOnTop: true,
                    	        visible: true
                        	};
                        	
                        	var console =  new A.Console(consoleSettings).render();
                    },
                    
                    _onMouseEnterResultsItem: function(e) {
                    	var instance = this;
                    	
        				var currentTarget = e.currentTarget;
        				currentTarget.addClass(CSS_SEARCH_RESULTS_ITEM_HOVER);
                    },
                    
                    _onMouseLeaveResultsItem: function(e) {
                    	var instance = this;
                    	
        				var currentTarget = e.currentTarget;
        				currentTarget.removeClass(CSS_SEARCH_RESULTS_ITEM_HOVER);
                    },
                    
                    _someFunction: function() {
                        var instance = this;
                    }

                }
            }
    );

    A.VapSearchResults = VapSearchResults;
        
    },1, {
        requires: [
            'aui-base',
            'console'
      ]
    }
);
