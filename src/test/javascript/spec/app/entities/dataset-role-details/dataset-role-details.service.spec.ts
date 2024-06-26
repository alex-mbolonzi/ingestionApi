import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { DatasetRoleDetailsService } from 'app/entities/dataset-role-details/dataset-role-details.service';
import { IDatasetRoleDetails, DatasetRoleDetails } from 'app/shared/model/dataset-role-details.model';

describe('Service Tests', () => {
  describe('DatasetRoleDetails Service', () => {
    let injector: TestBed;
    let service: DatasetRoleDetailsService;
    let httpMock: HttpTestingController;
    let elemDefault: IDatasetRoleDetails;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(DatasetRoleDetailsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new DatasetRoleDetails(0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
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

      it('should create a DatasetRoleDetails', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault,
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new DatasetRoleDetails(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a DatasetRoleDetails', () => {
        const returnedFromService = Object.assign(
          {
            datasetRoleDetailsId: 1,
            role: 'BBBBBB',
            email: 'BBBBBB',
            mudid: 'BBBBBB',
            name: 'BBBBBB',
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

      it('should return a list of DatasetRoleDetails', () => {
        const returnedFromService = Object.assign(
          {
            datasetRoleDetailsId: 1,
            role: 'BBBBBB',
            email: 'BBBBBB',
            mudid: 'BBBBBB',
            name: 'BBBBBB',
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

      it('should delete a DatasetRoleDetails', () => {
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
