const keyEventUtil = (() => {
    const nowDate = new Date();
    const nowDateString = `${nowDate.getFullYear()}-${nowDate.getMonth() > 8 ? nowDate.getMonth() + 1 : '0' + (nowDate.getMonth() + 1)}-${nowDate.getDate() > 9 ? nowDate.getDate() : '0' + nowDate.getDate()}`;

    const numberReg = /\D/;
    const digitNumberReg = /[^\d.]/;
    const hangulReg = /[^가-힣ㄱ-ㅎㅏ-ㅣ]/;
    const englishReg = /[^a-zA-Z]/;
    const noNumberStringReg = /[^가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z]/;
    const stringReg = /[^가-힣ㄱ-ㅎㅏ-ㅣ\w]/;
    const emailReg = /[^가-힣ㄱ-ㅎㅏ-ㅣ\w._-]/;
    const passwordReg = /[^\w!@#$&^*+=-]/;
    const numberRegAll = /\D/g;
    const digitNumberRegAll = /[^\d.]/;
    const hangulRegAll = /[^가-힣ㄱ-ㅎㅏ-ㅣ]/g;
    const englishRegAll = /[^a-zA-Z]/g;
    const noNumberStringRegAll = /[^가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z]/g;
    const stringRegAll = /[^가-힣ㄱ-ㅎㅏ-ㅣ\w]/g;
    const emailRegAll = /[^가-힣ㄱ-ㅎㅏ-ㅣ\w._-]/g;
    const passwordRegAll = /[^\w!@#$&^*+=-]/g;

    let ctrlPress = false;
    $(document).ready(function() {
        $(document).keydown(function (e) {
            ctrlPress = (e.metaKey || e.ctrlKey);
        }).keyup(function (e) {
            ctrlPress = (e.metaKey || e.ctrlKey);
        });
    })


    const onlyNumber = (elementId, maxLength) => {
        const el = 'string' === typeof elementId ? document.getElementById(elementId) : elementId;
        if(el?.tagName === 'INPUT') {
            if(isNaN(maxLength)) maxLength = -1;
            if(maxLength < 1) {
                el.addEventListener('keydown', (e) => {
                    if(ctrlPress && e.key === 'v' || e.key.length > 1) {
                        return true;
                    } else if(numberReg.test(e.key)) {
                        el.value = el.value.replaceAll(numberRegAll, '')
                        e.preventDefault()
                    }
                })
                el.addEventListener('paste', (e) => {
                    el.value = (e.clipboardData || window.clipboardData).getData("text").replaceAll(numberRegAll, '');
                    e.preventDefault();
                })
            } else {
                el.addEventListener('keydown', (e) => {
                    if(ctrlPress && e.key === 'v' || e.key.length > 1) {
                        return true;
                    } else if(numberReg.test(e.key) || el.value.length >= maxLength) {
                        el.value = el.value.replaceAll(numberRegAll, '')
                        e.preventDefault()
                    }
                })
                el.addEventListener('paste', (e) => {
                    el.value = (e.clipboardData || window.clipboardData).getData("text").replaceAll(numberRegAll, '');
                    if(el.value.length > maxLength) el.value = el.value.substring(0, maxLength);
                    e.preventDefault();
                })
            }
        }
    }

    const onlyDigitNumber = (elementId, maxLength) => {
        const el = 'string' === typeof elementId ? document.getElementById(elementId) : elementId;
        if(el?.tagName === 'INPUT') {
            if(isNaN(maxLength)) maxLength = -1;
            if(maxLength < 1) {
                el.addEventListener('keydown', (e) => {
                    if(ctrlPress && e.key === 'v' || e.key.length > 1) {
                        return true;
                    } else if(digitNumberReg.test(e.key)) {
                        el.value = el.value.replaceAll(digitNumberRegAll, '')
                        e.preventDefault()
                    }
                })
                el.addEventListener('paste', (e) => {
                    el.value = (e.clipboardData || window.clipboardData).getData("text").replaceAll(digitNumberRegAll, '');
                    e.preventDefault();
                })
            } else {
                el.addEventListener('keydown', (e) => {
                    if(ctrlPress && e.key === 'v' || e.key.length > 1) {
                        return true;
                    } else if(digitNumberReg.test(e.key) || el.value.length >= maxLength) {
                        el.value = el.value.replaceAll(digitNumberRegAll, '')
                        e.preventDefault()
                    }
                })
                el.addEventListener('paste', (e) => {
                    el.value = (e.clipboardData || window.clipboardData).getData("text").replaceAll(digitNumberRegAll, '');
                    if(el.value.length > maxLength) el.value = el.value.substring(0, maxLength);
                    e.preventDefault();
                })
            }
        }
    }

    const onlyHangul = (elementId, maxLength) => {
        const el = 'string' === typeof elementId ? document.getElementById(elementId) : elementId;
        if(el?.tagName === 'INPUT') {
            if(isNaN(maxLength)) maxLength = -1;
            if(maxLength < 1) {
                el.addEventListener('keydown', (e) => {
                    if(ctrlPress && e.key === 'v' || e.key.length > 1) {
                        return true;
                    } else if(hangulReg.test(e.key)) {
                        el.value = el.value.replaceAll(hangulRegAll, '')
                        e.preventDefault()
                    }
                })
                el.addEventListener('paste', (e) => {
                    el.value = (e.clipboardData || window.clipboardData).getData("text").replaceAll(hangulRegAll, '');
                    e.preventDefault();
                })
            } else {
                el.addEventListener('keydown', (e) => {
                    if(ctrlPress && e.key === 'v' || e.key.length > 1) {
                        return true;
                    } else if(hangulReg.test(e.key) || el.value.length >= maxLength) {
                        el.value = el.value.replaceAll(hangulRegAll, '')
                        e.preventDefault()
                    }
                })
                el.addEventListener('paste', (e) => {
                    el.value = (e.clipboardData || window.clipboardData).getData("text").replaceAll(hangulRegAll, '');
                    if(el.value.length > maxLength) el.value = el.value.substring(0, maxLength);
                    e.preventDefault();
                })
            }
        }
    }

    const onlyEnglish = (elementId, maxLength) => {
        const el = 'string' === typeof elementId ? document.getElementById(elementId) : elementId;
        if(el?.tagName === 'INPUT') {
            if(isNaN(maxLength)) maxLength = -1;
            if(maxLength < 1) {
                el.addEventListener('keydown', (e) => {
                    if(ctrlPress && e.key === 'v' || e.key.length > 1) {
                        return true;
                    } else if(englishReg.test(e.key)) {
                        el.value = el.value.replaceAll(englishRegAll, '')
                        e.preventDefault()
                    }
                })
                el.addEventListener('paste', (e) => {
                    el.value = (e.clipboardData || window.clipboardData).getData("text").replaceAll(englishRegAll, '');
                    e.preventDefault();
                })
            } else {
                el.addEventListener('keydown', (e) => {
                    if(ctrlPress && e.key === 'v' || e.key.length > 1) {
                        return true;
                    } else if(englishReg.test(e.key) || el.value.length >= maxLength) {
                        el.value = el.value.replaceAll(englishRegAll, '')
                        e.preventDefault()
                    }
                })
                el.addEventListener('paste', (e) => {
                    el.value = (e.clipboardData || window.clipboardData).getData("text").replaceAll(englishRegAll, '');
                    if(el.value.length > maxLength) el.value = el.value.substring(0, maxLength);
                    e.preventDefault();
                })
            }
        }
    }

    const onlyNoNumberString = (elementId, maxLength) => {
        const el = 'string' === typeof elementId ? document.getElementById(elementId) : elementId;
        if(el?.tagName === 'INPUT') {
            if(isNaN(maxLength)) maxLength = -1;
            if(maxLength < 1) {
                el.addEventListener('keydown', (e) => {
                    if(ctrlPress && e.key === 'v' || e.key.length > 1) {
                        return true;
                    } else if(noNumberStringReg.test(e.key)) {
                        el.value = el.value.replaceAll(noNumberStringRegAll, '')
                        e.preventDefault()
                    }
                })
                el.addEventListener('paste', (e) => {
                    el.value = (e.clipboardData || window.clipboardData).getData("text").replaceAll(noNumberStringRegAll, '');
                    e.preventDefault();
                })
            } else {
                el.addEventListener('keydown', (e) => {
                    if(ctrlPress && e.key === 'v' || e.key.length > 1) {
                        return true;
                    } else if(noNumberStringReg.test(e.key) || el.value.length >= maxLength) {
                        el.value = el.value.replaceAll(noNumberStringRegAll, '')
                        e.preventDefault()
                    }
                })
                el.addEventListener('paste', (e) => {
                    el.value = (e.clipboardData || window.clipboardData).getData("text").replaceAll(noNumberStringRegAll, '');
                    if(el.value.length > maxLength) el.value = el.value.substring(0, maxLength);
                    e.preventDefault();
                })
            }
        }
    }

    const onlyString = (elementId, maxLength) => {
        const el = 'string' === typeof elementId ? document.getElementById(elementId) : elementId;
        if(el?.tagName === 'INPUT') {
            if(isNaN(maxLength)) maxLength = -1;
            if(maxLength < 1) {
                el.addEventListener('keydown', (e) => {
                    if(ctrlPress && e.key === 'v' || e.key.length > 1) {
                        return true;
                    } else if(stringReg.test(e.key)) {
                        el.value = el.value.replaceAll(stringRegAll, '')
                        e.preventDefault()
                    }
                })
                el.addEventListener('paste', (e) => {
                    el.value = (e.clipboardData || window.clipboardData).getData("text").replaceAll(stringRegAll, '');
                    e.preventDefault();
                })
            } else {
                el.addEventListener('keydown', (e) => {
                    if(ctrlPress && e.key === 'v' || e.key.length > 1) {
                        return true;
                    } else if(stringReg.test(e.key) || el.value.length >= maxLength) {
                        el.value = el.value.replaceAll(stringRegAll, '')
                        e.preventDefault()
                    }
                })
                el.addEventListener('paste', (e) => {
                    el.value = (e.clipboardData || window.clipboardData).getData("text").replaceAll(stringRegAll, '');
                    if(el.value.length > maxLength) el.value = el.value.substring(0, maxLength);
                    e.preventDefault();
                })
            }
        }
    }

    const onlyEmailString = (elementId, maxLength) => {
        const el = 'string' === typeof elementId ? document.getElementById(elementId) : elementId;
        if(el?.tagName === 'INPUT') {
            if(isNaN(maxLength)) maxLength = -1;
            if(maxLength < 1) {
                el.addEventListener('keydown', (e) => {
                    if(ctrlPress && e.key === 'v' || e.key.length > 1) {
                        return true;
                    } else if(emailReg.test(e.key)) {
                        el.value = el.value.replaceAll(emailRegAll, '')
                        e.preventDefault()
                    }
                })
                el.addEventListener('paste', (e) => {
                    el.value = (e.clipboardData || window.clipboardData).getData("text").replaceAll(emailRegAll, '');
                    e.preventDefault();
                })
            } else {
                el.addEventListener('keydown', (e) => {
                    if(ctrlPress && e.key === 'v' || e.key.length > 1) {
                        return true;
                    } else if(emailReg.test(e.key) || el.value.length >= maxLength) {
                        el.value = el.value.replaceAll(emailRegAll, '')
                        e.preventDefault()
                    }
                })
                el.addEventListener('paste', (e) => {
                    el.value = (e.clipboardData || window.clipboardData).getData("text").replaceAll(emailRegAll, '');
                    if(el.value.length > maxLength) el.value = el.value.substring(0, maxLength);
                    e.preventDefault();
                })
            }
        }
    }

    const onlyPasswordString = (elementId, maxLength) => {
        const el = 'string' === typeof elementId ? document.getElementById(elementId) : elementId;
        if(el?.tagName === 'INPUT') {
            if(isNaN(maxLength)) maxLength = -1;
            if(maxLength < 1) {
                el.addEventListener('keydown', (e) => {
                    if(ctrlPress && e.key === 'v' || e.key.length > 1) {
                        return true;
                    } else if(passwordReg.test(e.key)) {
                        el.value = el.value.replaceAll(passwordRegAll, '')
                        e.preventDefault()
                    }
                })
                el.addEventListener('paste', (e) => {
                    el.value = (e.clipboardData || window.clipboardData).getData("text").replaceAll(passwordRegAll, '');
                    e.preventDefault();
                })
            } else {
                el.addEventListener('keydown', (e) => {
                    if(ctrlPress && e.key === 'v' || e.key.length > 1) {
                        return true;
                    } else if(passwordReg.test(e.key) || el.value.length >= maxLength) {
                        el.value = el.value.replaceAll(passwordRegAll, '')
                        e.preventDefault()
                    }
                })
                el.addEventListener('paste', (e) => {
                    el.value = (e.clipboardData || window.clipboardData).getData("text").replaceAll(passwordRegAll, '');
                    if(el.value.length > maxLength) el.value = el.value.substring(0, maxLength);
                    e.preventDefault();
                })
            }
        }
    }

    const focusNextInput = (elementId1, elementId2, maxLength) => {
        const el1 = 'string' === typeof elementId1 ? document.getElementById(elementId1) : elementId1;
        const el2 = 'string' === typeof elementId2 ? document.getElementById(elementId2) : elementId2;
        if(el1.tagName === 'INPUT' && el2.tagName === 'INPUT' && maxLength > 0) {
            el1.addEventListener('keyup', (e) => {
                if(el1.value.length === maxLength && e.key.length <= 1) el2.focus();
            })
        }
    }

    const numberAmount = (elementId, maxLength) => {
        const el = 'string' === typeof elementId ? document.getElementById(elementId) : elementId;
        if(el?.tagName === 'INPUT') {
            if(maxLength < 1) {
                el.value = el.value.replaceAll(',', '').replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                el.addEventListener('keyup', () => {
                    el.value = el.value.replaceAll(',', '').replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                })
            } else {
                el.value = el.value.replaceAll(',', '').replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                el.addEventListener('keyup', () => {
                    el.value = el.value.substring(0, maxLength).replaceAll(',', '').replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                })
            }
        }
    }

    const numberAmountSumSource = (sourceId, targetId) => {
        const source = 'string' === typeof sourceId ? document.getElementById(sourceId) : sourceId;
        const target = 'string' === typeof targetId ? document.getElementById(targetId) : targetId;
        if(source?.tagName === 'INPUT' && target?.tagName === 'INPUT' || target?.tagName === 'SPAN') {
            const amountEvent = new CustomEvent('changeAmount')
            source.addEventListener('keyup', () => { target.dispatchEvent(amountEvent) })
        }
    }

    const numberAmountSumTarget = (sourceName, targetId) => {
        const target = 'string' === typeof targetId ? document.getElementById(targetId) : targetId;
        if(target?.tagName === 'INPUT' || target?.tagName === 'SPAN') {
            const tOption = target.tagName === 'INPUT' ? 'value' : 'textContent'
            target.addEventListener('changeAmount', () => {
                let sum = 0;
                document.querySelectorAll(`input[name=${sourceName}]`).forEach((el) => {
                    sum += Number(el.value.replaceAll(',', ''));
                })
                target[tOption] = sum.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
            })
        }
    }

    /**
     * input 안에 data-input-type 입력
     *
     * data-input-type 종류
     *
     * - number
     * - digitNumber
     * - hangul
     * - english
     * - noNumberString
     * - string
     * - email
     * - password
     * @param elementId
     */
    const setInputTypeByForm = (elementId) => {
        const el = 'string' === typeof elementId ? document.getElementById(elementId) : elementId;
        if(el?.tagName === 'FORM') {
            $(el).find('input[data-input-type]').toArray().forEach((inputEl) => {
                switch (inputEl.dataset.inputType) {
                    case 'number':
                        onlyNumber(inputEl, inputEl.maxLength);
                        break;
                    case 'digitNumber':
                        onlyDigitNumber(inputEl, inputEl.maxLength);
                        break;
                    case 'hangul':
                        onlyHangul(inputEl, inputEl.maxLength);
                        break;
                    case 'english':
                        onlyEnglish(inputEl, inputEl.maxLength);
                        break;
                    case 'noNumberString':
                        onlyNoNumberString(inputEl, inputEl.maxLength);
                        break;
                    case 'string':
                        onlyString(inputEl, inputEl.maxLength);
                        break;
                    case 'email':
                        onlyEmailString(inputEl, inputEl.maxLength);
                        break;
                    case 'password':
                        onlyPasswordString(inputEl, inputEl.maxLength);
                        break;

                }
            })
            $(el).find('input[data-next-input]').toArray().forEach((inputEl) => {
                focusNextInput(inputEl, inputEl.dataset.nextInput, inputEl.maxLength);
            })
            $(el).find('input[data-amount-value]').toArray().forEach((inputEl) => {
                if(inputEl.dataset.amountValue === 'true') numberAmount(inputEl);
            })
            $(el).find('[data-amount-target]').toArray().forEach((el) => {
                numberAmountSumTarget(el.dataset.amountTarget, el);
            })
            $(el).find('input[data-amount-source]').toArray().forEach((el) => {
                numberAmountSumSource(el, el.dataset.amountSource);
            })
        }
    }

    return {
        onlyNumber, onlyHangul, onlyEnglish, onlyNoNumberString, onlyString, onlyEmailString, onlyPasswordString, focusNextInput, setInputTypeByForm, numberAmount, numberAmountSumSource, numberAmountSumTarget
    }
})()