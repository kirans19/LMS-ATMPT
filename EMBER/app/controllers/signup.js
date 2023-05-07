import Controller from '@ember/controller';

import { tracked } from '@glimmer/tracking';
import { action } from '@ember/object';

export default class SignupController extends Controller {
  @tracked email = '';
  @tracked password = '';
  @tracked first_name = '';
  @tracked last_name = '';
  @tracked mobile_no = '';
  @tracked address = '';
  @tracked user_type = '';

  @action
  selectOption(value) {
    this.user_type = value;

    this.args.onChange(value);
  }

  @action
  async submitForm(event) {
    event.preventDefault();

    console.log(this.email);

    const response = await fetch('http://localhost:8080/LMS_ATMPT/signup', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        email: this.email,
        password: this.password,
        first_name: this.first_name,
        last_name: this.last_name,
        mobile_no: this.mobile_no,
        address: this.address,
        user_type: this.user_type,
      }),
    });

    if (response.ok) {
      console.log('Signup successful!');
      const data = await response.json();
      console.log(data.success);
      console.log('Signup successful!');
    } else {
      console.error('Signup failed!');
    }
  }
}
