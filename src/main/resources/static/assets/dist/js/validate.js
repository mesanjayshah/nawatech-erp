(function() {
  'use strict';
  window.addEventListener('load', function() {
    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    $('.needs-validation .invalid-feedback').remove();
    var forms = document.getElementsByClassName('needs-validation');
    // Loop over them and prevent submission
    var validation = Array.prototype.filter.call(forms, function(form) {
      $(form).validate({
        errorElement : 'span',
        ignore: ":hidden:not(.ckeditor, .ckeditor-basic, select.default), .ignore",
        debug: false,
        errorPlacement: function(error, element) {
          $(element).closest('div').append(error);
        }
      });

      $.each($(".ckeditor.required, .ckeditor-basic.required"), function () {
        $(this).rules("add", {
          required: function()
          {
            // CKEDITOR.instances.cktext.updateElement();
            for (var i in CKEDITOR.instances){
              CKEDITOR.instances[i].updateElement();
            }
          },

          minlength:1
        });
      });
      form.addEventListener('submit', function(event) {
        if ($(form).valid() === false) {
          event.preventDefault();
          event.stopPropagation();
        }
        form.classList.add('was-validated');
      }, false);
    });
  }, false);
})();

// handle removing of select2 error on select
$('select.default').on('select2:select', function (e) {
  if($(this).val()){
    $(this).closest('div').find('.error').remove();
  }
});

function isValidForm(instance) {
  if(!$(instance).hasClass("was-validated")) $(instance).addClass("was-validated");
  return $(instance).valid();
}