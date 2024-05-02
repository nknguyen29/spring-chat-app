/**
 * Hello! 👋
 * Welcome to your app's main JavaScript file.
 *
 * This is the main entry point for the chatapp module.
 * From here, you can import other JavaScript files, stylesheets, or any other assets.
 * This file is bundled by Vite and loaded by the chatapp module.
 */

// Import the styles from the app.css file
import 'css/app.css';

import 'flowbite'; // Import Flowbite

// Font Awesome 6.5.2 by @fontawesome - https://fontawesome.com
//
// The Free and Pro packages include not only the icons in all the styles, but
// also all the supporting files to make them render.
// We do not recommend using this package.
// This package closely resembles what you get from our Downloads page.
// You have a lot of options with this package but it’s also the largest.
// https://docs.fontawesome.com/web/setup/packages#all-the-things

import '@fortawesome/fontawesome-free/css/all.min.css';
// import '@fortawesome/fontawesome-free/js/all.js';

/**
 * Register an event at the document for the specified selector,
 * so events are still catched after DOM changes.
 */
function handleEvent(eventType, selector, handler) {
  document.addEventListener(eventType, function (event) {
    if (event.target.matches(selector + ', ' + selector + ' *')) {
      handler.apply(event.target.closest(selector), arguments);
    }
  });
}

handleEvent('submit', '.js-submit-confirm', function (event) {
  if (!confirm(this.getAttribute('data-confirm-message'))) {
    event.preventDefault();
    return false;
  }
  return true;
});

handleEvent('click', 'body', function (event) {
  // close any open dropdown
  const $clickedDropdown = event.target.closest('.js-dropdown');
  const $dropdowns = document.querySelectorAll('.js-dropdown');
  $dropdowns.forEach(($dropdown) => {
    if ($clickedDropdown !== $dropdown && $dropdown.getAttribute('data-dropdown-keepopen') !== 'true') {
      $dropdown.ariaExpanded = 'false';
      $dropdown.nextElementSibling.classList.add('hidden');
    }
  });
  // toggle selected if applicable
  if ($clickedDropdown) {
    $clickedDropdown.ariaExpanded = '' + ($clickedDropdown.ariaExpanded !== 'true');
    $clickedDropdown.nextElementSibling.classList.toggle('hidden');
    event.preventDefault();
  }
});
