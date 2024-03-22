const addSearchFormElementToTargetForm = (target, searchForm) => {
    searchForm.querySelectorAll('input, select').forEach((el) => {
        if(el.name !== undefined) {
        console.log(el);
            el.type = 'hidden';
            el.className = 'd-none'
            target.appendChild(el);
        }
    })
}

const addSearchFormDataToHiddenInputInForm = (target, searchFormData) => {
    Object.keys(searchFormData).forEach((key) => {
        if(searchFormData[key] !== undefined) {
            const input = document.createElement('input');
            input.type = 'hidden';
            input.name = key;
            input.value = searchFormData[key];
            target.appendChild(input);
        }
    })
}