let valuebox = document.getElementById('valuebox');

import * as foo from './m02_foo.mjs';

export function valueUP() {
    valuebox.innerText = +valuebox.innerText + 1;
    foo.test();
}