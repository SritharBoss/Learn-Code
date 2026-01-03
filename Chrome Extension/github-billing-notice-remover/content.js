function removeBillingError() {
  const errorSections = document.querySelectorAll(
    'section.flash.flash-full.js-notice.flash-error[aria-label="Error"]'
  );

  errorSections.forEach(section => {
    if (section.innerText.includes("problem billing your account")) {
      section.remove();
      console.log("GitHub billing error notification removed");
    }
  });
}

// Run once initially
removeBillingError();

// Observe DOM changes (GitHub uses dynamic navigation)
const observer = new MutationObserver(() => {
  removeBillingError();
});

observer.observe(document.body, {
  childList: true,
  subtree: true
});
