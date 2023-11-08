export default {
  isClamp(event) {
    /**
     * @param {*} event  
     * @returns {Boolean} 
     */
    try {
      const width = event.target.clientWidth;
      const originWidth = event.target.nextElementSibling.clientWidth;
      return originWidth > width;
    } catch (error) {
      return false;
    }
  },

 
  scroll(el) {
    /**
     * @this {VM} 
     *            
     * @argument {DomElement} el 
     * @argument {Number} height 
     */
    el.onscroll = ({target}) => {

      const {scrollTop, scrollHeight, clientHeight} = target;
 
      if (this.hasNext && this.finish && scrollHeight - scrollTop <= clientHeight + 100) {
        this.loadMore();
      }
    };
  },


  showtime(time) {
    let date = typeof time === "number" ? new Date(time) : new Date((time || "").replace(/-/g, "/"));
    
    let secondTime = (new Date().getTime() - date.getTime()) / 1000;
    
    let dayTime = Math.floor(secondTime / 86400);

    let isValidDate = Object.prototype.toString.call(date) === "[object Date]" && !isNaN(date.getTime());

    if (!isValidDate) {
      window.console.error("Not a valid date");
    }
    const formatDate = function (date) {
      let today = new Date(date);
      let year = today.getFullYear();
      let month = ("0" + (today.getMonth() + 1)).slice(-2);
      let day = ("0" + today.getDate()).slice(-2);
      let hour = today.getHours();
      let minute = today.getMinutes();
      let second = today.getSeconds();
      return `${year}-${month}-${day} ${hour}:${minute}:${second}`;
    };

    
    if (isNaN(dayTime) || dayTime < 0) {
      return formatDate(date);
    }

    return (
        (dayTime === 0 && ((secondTime < 60 && "Just now") ||
            (secondTime < 120 && "1min ago") ||
            (secondTime < 3600 && Math.floor(secondTime / 60) + "mins ago") ||
            (secondTime < 7200 && "1hour ago") ||
            (secondTime < 86400 && Math.floor(secondTime / 3600) + "hours ago"))) ||
        (dayTime === 1 && "yesterday") ||
        (dayTime < 7 && dayTime + "days before") ||
        (dayTime < 30 && Math.floor(dayTime / 7) + "weeks before") ||
        (dayTime < 365 && Math.floor(dayTime / 30) + "months before") ||
        (dayTime >= 365 && Math.floor(dayTime / 365) + "years before")
    );
  },


  toToc(data) {

    data = data.match(/<[hH][1-6]>.*?<\/[hH][1-6]>/g);
    if (!data) {
      return null;
    }
    const levelStack = [];
    let result = '';
    const addStartUL = function () {
      result += '<ul class="catalog-list">';
    };
    const addEndUL = function () {
      result += '</ul>\n';
    };
    const addLI = function (index, itemText) {
      result += '<li><a class="toc-link' + '-#' + index + '" href="#' + index + '">' + itemText + "</a></li>\n";
    };
    data.forEach(function (item, index) {

      let a_id = item.replace(/<a id="(.*?)"([^\[]*)/g, '$1').replace(/<[hH][1-6]>/g, '');

      const itemText = item.replace(/<[^>]+>/g, '');

      const itemLabel = item.match(/<\w+?>/)[0];

      let levelIndex = levelStack.indexOf(itemLabel);

      if (levelIndex === -1) {
        levelStack.unshift(itemLabel);
        addStartUL();
        addLI(a_id, itemText);
      }

      else if (levelIndex === 0) {
        addLI(a_id, itemText);
      }

      else {
        while (levelIndex--) {
          levelStack.shift();
          addEndUL();
        }
        addLI(a_id, itemText);
      }
    });

    while (levelStack.length) {
      levelStack.shift();
      addEndUL();
    }


    return result.replace(/\r?\n|\r/g, "");
  },

};
