import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { EmailTemplateService } from 'app/entities/email-template/email-template.service';
import { IEmailTemplate, EmailTemplate } from 'app/shared/model/email-template.model';

describe('Service Tests', () => {
  describe('EmailTemplate Service', () => {
    let injector: TestBed;
    let service: EmailTemplateService;
    let httpMock: HttpTestingController;
    let elemDefault: IEmailTemplate;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(EmailTemplateService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new EmailTemplate(0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a EmailTemplate', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault,
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new EmailTemplate(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a EmailTemplate', () => {
        const returnedFromService = Object.assign(
          {
            templateId: 1,
            templateCode: 'BBBBBB',
            subject: 'BBBBBB',
            body: 'BBBBBB',
          },
          elemDefault,
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of EmailTemplate', () => {
        const returnedFromService = Object.assign(
          {
            templateId: 1,
            templateCode: 'BBBBBB',
            subject: 'BBBBBB',
            body: 'BBBBBB',
          },
          elemDefault,
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body),
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a EmailTemplate', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
